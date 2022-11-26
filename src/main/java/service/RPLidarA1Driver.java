package service;

import com.rm5248.serial.NoSuchPortException;
import com.rm5248.serial.NotASerialPortException;
import com.rm5248.serial.SerialPort;
import commands.LidarCommands;
import gui.Mapping;
import gui.ShowGui;
import model.GetLidarInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

public class RPLidarA1Driver extends GetLidarInfo {
    private boolean scanStarted = false;
    SerialPort serialPort = openSerialPort();

    public void readData() {
        if (!scanStarted){
            startLidarScan();
            scanStarted = true;
        }
        int size = 0;
        byte[] lastData = new byte[0];
        InputStream inputStream = null;
        try {
            inputStream = serialPort.getInputStream();
            byte[] ldata = inputStream.readNBytes(8000);

            if (ldata != null) {
                int lastLength = lastData.length;
                int dataLength = ldata.length;
                size = lastLength + dataLength;
                byte[] dataa = new byte[size];

                for (int i = 0; i < size; i++) {
                    if (i < lastLength) {
                        dataa[i] = lastData[i];
                    } else {
                        dataa[i] = ldata[i - lastLength];
                    }
                }

                int lastsize = 0;
                while (size > 0 && size != lastsize) {
                    int used = parseData(dataa, size);
                    lastData = new byte[size - used];
                    for (int i = 0; i < size - used; i++) {
                        dataa[i] = dataa[i + used];
                        lastData[i] = dataa[i];
                    }
                    lastsize = size;
                    size -= used;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean parseScan(byte[] data, int offset) {
        byte b0 = data[offset];
        byte b1 = data[offset + 1];
        boolean start0 = (b0 & 0x01) == 1;
        boolean start1 = (b0 & 0x02) >> 1 == 1;

        if (start0 == start1)
            return false;

        if ((b1 & 0x01) != 1)
            return false;

        double angel = (((b1 & 0xFF) | ((data[offset + 2] & 0xFF) << 8)) >> 1) / 64.0;
        double distance = (((data[offset + 3] & 0xFF) | ((data[offset + 4] & 0xFF) << 8)) / 4.0) / 10.0;

        if (distance != 0 && distance <= 150) {
            this.setAngle(angel);
            this.setDistance(distance);
        }
        return true;
    }

    private int parseData(byte[] data, int length) {
        int offset = 0;
        while (true) {
            if (offset + 5 > length) {
                return offset;
            }
            if (parseScan(data, offset)) {
                offset += 5;
            } else {
                offset += 1;
            }
        }
    }

    private void startLidarScan() {
        OutputStream outputStream = null;
        byte[] commands = new byte[2];
        commands[0] = getCommandType(LidarCommands.START);
        commands[1] = getCommandType(LidarCommands.SCAN);
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(commands);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public SerialPort openSerialPort() {
        SerialPort serialPort = null;
        try {
            if (serialPort == null) {
                if (portExist("COM4")){
                    serialPort = new SerialPort("COM4", SerialPort.BaudRate.B115200, SerialPort.DataBits.DATABITS_8
                            , SerialPort.StopBits.STOPBITS_1, SerialPort.Parity.NONE, SerialPort.FlowControl.NONE);
                }
            }
        } catch (NoSuchPortException e) {
            throw new RuntimeException(e);
        } catch (NotASerialPortException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serialPort;
    }

    private byte getCommandType(LidarCommands command) {
        switch (command) {
            case START:
                return (byte) 0xA5;
            case SCAN:
                return (byte) 0x20;
            case STOP:
                return (byte) 0x25;
            default:
                return (byte) 0x00;
        }
    }

    private boolean portExist(String portName){
        String[] portNames;
        try {
            portNames = SerialPort.getSerialPorts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (portNames != null){
            for (String name : portNames){
                if (name.equals(portName))
                    return true;
            }
        }
        return false;
    }

//    public List<Double> getAngleList(){
//        return this.getAngle();
//    }
//    public List<Double> getDistanceList(){
//        return this.getDistance();
//    }

    public void clearLists(){
        this.getDistance().clear();
        this.getAngle().clear();
    }
}
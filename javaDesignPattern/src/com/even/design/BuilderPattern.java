package com.even.design;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/14
 */
public class BuilderPattern {

    /*房子，即要建造的产品*/
    class Room {
        /*房子有窗*/
        private String window;
        /*房子有门*/
        private String door;
        /*房子有地板*/
        private String floor;

        public String getWindow() {
            return window;
        }

        public void setWindow(String window) {
            this.window = window;
        }

        public String getDoor() {
            return door;
        }

        public void setDoor(String door) {
            this.door = door;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }
    }

    /*定义建造方法*/
    interface Builder {
        void makeWindow();

        void makeDoor();

        void makeFloor();

        /*建好的房子*/
        Room getRoom();
    }

    class RoomBuilder implements Builder {

        private Room room = new Room();

        @Override
        public void makeWindow() {
            /*四扇窗*/
            room.setWindow("1");
        }

        @Override
        public void makeDoor() {
            room.setDoor("1");
        }

        @Override
        public void makeFloor() {
            room.setFloor("1");
        }

        @Override
        public Room getRoom() {
            return room;
        }
    }

    /*设计师，负责指导builder按照怎么样的顺序来完成房子product的建造*/
    class Design {
        public void command(Builder builder) {
            /*先建地板*/
            builder.makeFloor();
            /*再建门*/
            builder.makeDoor();
            /*最后建窗*/
            builder.makeWindow();
        }
    }

    /*build开始*/
    public void client() {
        /*先找一个工人*/
        Builder builder = new RoomBuilder();
        /*再找一个设计师*/
        Design design = new Design();
        /*设计师要工人去工作*/
        design.command(builder);
        /*工人造完房子*/
        Room newRoom = builder.getRoom();
    }

}

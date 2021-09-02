package com.mikiandor.welearn.Interfaces;

public class Auth_Interfaces {


   public interface CreateUser_callBack{
        void success();
        void failed(String message);
    }


    public interface Login_callBack{
        void success();
        void failed(String message);
    }

}

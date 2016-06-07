package com.fpd.model.login;

import java.io.Serializable;

/**
 * Created by solo on 2016/6/7.
 */
public class LRDataEntity implements Serializable
{
    private int userId;

   public int getUserId()
   {
       return userId;
   }

   public void setUserId(int userId)
   {
       this.userId=userId;
   }
}

package com.fpd.model.sportnews;

/**
 * Created by solo on 2016/6/5.
 */

import java.io.Serializable;

/**
 *  "title": "致敬阿里！上帝带走了最伟大的冠军",
 *  "url": "http:\/\/www.3023.com\/3\/398325757.html",
 *  "img": "http:\/\/mmbiz.qpic.cn\/mmbiz\/C4icBnA96NrVY3HRHz6WUicsNYStdXht6PZeHBnR1R4TDZMztARPT3ibNCSTLavYZxL0jNnVz8YjC93mqym96uDPQ\/0?wx_fmt=jpeg",
 *  "author": "新浪体育",
 *  "time": 1465027860
**/
public class ArticleEntity implements Serializable
{
    public String title;
    public String url;
    public String img;
    public String author;
    public long time;
    public boolean isReaded;

    public String getTitle()
    {
        return this.title;
    }

    public String getUrl()
    {
        return url;
    }

    public String getImg()
    {
        return img;
    }

    public String getAuthor()
    {
        return author;
    }

    public long getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return "{"+"\n"+
                "title:"+title+"\n"+
                "url:"+url+"\n"+
                "imr:"+img+"\n"+
                "author:"+author+"\n"+
                "time:"+time+"\n" +
                "}\n";
    }
}

package com.fpd.model.sportnews;

import java.io.Serializable;
import java.util.List;

/**
 * Created by solo on 2016/6/5.
 */
public class SportNewsEntityData implements Serializable
{
    public String channel;
    public List<ArticleEntity> article;

    @Override
    public String toString()
    {
        return "{channel:"+"channel"+"\n"+
                "article:"+article+"\n";
    }
}

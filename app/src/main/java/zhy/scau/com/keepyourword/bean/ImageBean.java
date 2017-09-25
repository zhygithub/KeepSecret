package zhy.scau.com.keepyourword.bean;

/**
 * Created by ZhengHy on 2017-09-18.
 */

public class ImageBean {
    private String path;
    private long time;
    private String name;

    public ImageBean(String path, long time, String name) {
        this.path = path;
        this.time = time;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

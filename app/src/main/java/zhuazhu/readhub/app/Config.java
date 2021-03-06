package zhuazhu.readhub.app;

/**
 * @author zhuazhu
 **/
public interface Config {
    /**
     * 连接超时时间
     */
    long CONNECTION_TIMEOUT = 3;
    /**
     * 读取超时时间
     */
    long READ_TIMEOUT = 3;
    /**
     * 写入超时时间
     */
    long WRITE_TIMEOUT = 3;
    /**
     * bugly的appid
     */
    String BUGLY_APP_ID = "f6fea55036";
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
}

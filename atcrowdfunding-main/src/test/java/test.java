import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther:shisheng
 * @creat 2020-05-10-22:50
 */
public class test {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(test.class);
        logger.debug("debug..."); //用于调试程序
        logger.info("info...");//用于请求处理提示消息
        logger.warn("warn...");//用于警告处理提示消息
        logger.error("error...");//用于异常处理提示消息
        logger.error("==>>"+logger.getClass());

    }
}

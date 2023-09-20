package cloud.tianai.config;

import ch.qos.logback.core.PropertyDefinerBase;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * @Author AIM
 * @Des 日志路径
 * @DATE 2022/7/20
 */
public class LogPathDefiner extends PropertyDefinerBase {

	@Override
	public String getPropertyValue() {
		ApplicationHome h = new ApplicationHome(getClass());
		File jarF = h.getSource();
		String LogPath = jarF.getParentFile().toString() + File.separator + "logs" + File.separator;
		System.out.println(" - 日志存放路径: " + LogPath);
		return LogPath;
	}
}
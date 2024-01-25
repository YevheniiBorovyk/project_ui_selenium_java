package core;

import io.qameta.allure.Attachment;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import static core.BaseTest.getInstantiatedDriver;

@Log4j
public class AllureAttachments {

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] attachScreenshot(String name) {
        return ((TakesScreenshot) getInstantiatedDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{1}", type = "application/json")
    public static String jsonAttachment(String json, String name) {
        return json;
    }

    private static byte[] getBytesFromBufferedImage(BufferedImage bufferedImage) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("Failed to write BufferedImage to ByteArrayOutputStream.\n" + e.getMessage());
            return new byte[0];
        }
    }

    private static byte[] getBytesFromFile(String filePath) {
        byte[] fileAsByteArray = "".getBytes();
        try {
            fileAsByteArray = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            log.error(e);
        }
        return fileAsByteArray;
    }
}

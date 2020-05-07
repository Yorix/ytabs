package com.yorix.ytabs.service;

import com.yorix.ytabs.configuration.AppProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageService {
    private final AppProperties appProperties;

    public ImageService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String[] getImages(String url) {
        url = url.replaceAll("^/img/get-all/(http(s)?:(/)*)?", "http://");

        Document doc;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/81.0.4044.129 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(10000)
                    .followRedirects(true)
                    .get();
        } catch (IOException e) {
            return new String[]{"Incorrect url"};
        }

        Stream<String> stream = getStream(doc);

        List<String> thumbnails = stream.collect(Collectors.toList());
        return thumbnails.toArray(String[]::new);
    }

    private Stream<String> getStream(Document doc) {
        Stream<String> imgStream = doc.select("img").stream()
                .map(element -> element.absUrl("src").replaceAll("\\?.*", ""))
                .filter(s -> {
                    try {
                        BufferedImage img = ImageIO.read(new URL(s));
                        if (img != null)
                            return img.getWidth() > 30 && img.getHeight() > 30;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .limit(30);
        Stream<String> linkStream = doc.select("link").stream()
                .filter(element ->
                        element.attr("rel").matches("(.*)icon|image(.*)"))
                .map(element -> element.absUrl("href"))
                .limit(10);
        Stream<String> metaStream = doc.select("meta").stream()
                .filter(element -> element.attr("content").matches("(.*)png|(.*)jpg|(.*)gif|(.*)ico"))
                .map(element -> element.absUrl("content"))
                .limit(10);

        Stream<String> stream = Stream.concat(linkStream, imgStream);
        return Stream.concat(stream, metaStream);
    }

    public String downloadImg(String urlStr, String oldFilename) throws IOException {
        String filename = UUID.randomUUID().toString();
        String extend = urlStr.replaceAll("^.*(?=\\.\\w+$)", "");
        filename = filename.concat(extend);
        String directory = appProperties.getImageStorageLocation();
        File file = new File(directory.concat(filename));
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bis.readAllBytes());
        file = new File(directory.concat(oldFilename));
        file.delete();
        bis.close();
        fos.close();
        return filename;
    }
}

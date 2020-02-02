package com.yorix.ytabs.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UrlService {
    public String[] getImages(String rawUrl) {
        if (!rawUrl.startsWith("http"))
            rawUrl = "http://".concat(rawUrl);

        URL url = null;
        try {
            url = new URL(rawUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Document doc;
        try {
            doc = Jsoup.parse(url, 10000);
        } catch (IOException e) {
            return new String[]{"Incorrect url"};
        }

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

        List<String> thumbs = Stream.concat(linkStream, imgStream).collect(Collectors.toList());
        return thumbs.toArray(String[]::new);
    }
}

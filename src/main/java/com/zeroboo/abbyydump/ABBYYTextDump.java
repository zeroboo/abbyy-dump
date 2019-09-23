/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeroboo.abbyydump;

import ABBYY.AltoType;
import ABBYY.BlockType;
import ABBYY.ComposedBlockType;
import ABBYY.PageSpaceType;
import ABBYY.PageType;
import ABBYY.TextBlockType;
import ABBYY.TextBlockType.TextLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pthung
 */
public class ABBYYTextDump {

    Logger logger = LoggerFactory.getLogger(ABBYYTextDump.class.getName());
    String abbyyXml;
    String outputFolder = "";

    public ABBYYTextDump(String abbyyOutputFile, String outputFolder) {
        this.abbyyXml = abbyyOutputFile;
        this.outputFolder = outputFolder;
        prepareOutputDir();
        logger.info("constructor: input={}, output={}", this.abbyyXml, this.outputFolder);
    }

    private void prepareOutputDir() {
        try {
            if (!Files.exists(Paths.get(outputFolder))) {

                Files.createDirectories(Paths.get(outputFolder));
            }
        } catch (IOException ex) {
            logger.error("Create output dir failed", ex);
        }
    }

    public void print() throws IOException, JAXBException {
        ///final JAXBContext jaxbContext = JAXBContext.newInstance(ABBYYResult.class);
        final JAXBContext jaxbContext = JAXBContext.newInstance(AltoType.class);
        final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        final JAXBElement root = (JAXBElement) jaxbUnmarshaller.unmarshal(new File(abbyyXml));
        final AltoType customer = (AltoType) root.getValue();
        logger.debug("customer={}", customer.getLayout().getPage().toString());

        for (PageType page : customer.getLayout().getPage()) {
            int width = page.getWIDTH().intValue();
            int height = page.getHEIGHT().intValue();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            logger.debug("page={}, width={}, height={}", page.getID(), width, height);
            // Create a graphics which can be used to draw into the buffered image
            Graphics2D g2d = bufferedImage.createGraphics();
            // fill all the image with white
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, width, height);
            Stroke blockStroke = new BasicStroke(2);
            Stroke pageStroke = new BasicStroke(3);
            Stroke textStroke = new BasicStroke(1);

            PageSpaceType printSpace = page.getPrintSpace();
            g2d.setStroke(pageStroke);
            g2d.setColor(Color.BLUE);
            g2d.drawRect((int) printSpace.getHPOS(),
                    (int) printSpace.getHPOS(),
                    (int) printSpace.getWIDTH(),
                    (int) printSpace.getHEIGHT());

            List<TextItem> allText = new ArrayList<>();
            List<TextLine> allLines = new ArrayList<>();
            for (BlockType block : printSpace.getTextBlockOrIllustrationOrGraphicalElement()) {
                if (block instanceof TextBlockType) {
                    TextBlockType textBlock = (TextBlockType) block;
                    extractTextItem(page.getID(), textBlock, allText, allLines);
                } else if (block instanceof ABBYY.ComposedBlockType) {
                    extractTextItem(page.getID(), (ABBYY.ComposedBlockType) block, allText, allLines);

                }
                g2d.setColor(Color.GREEN);
                g2d.setStroke(blockStroke);
                g2d.drawString(block.getID(),
                        (int) block.getHPOS(),
                        (int) block.getHPOS());
                g2d.drawRect((int) block.getHPOS(),
                        (int) block.getHPOS(),
                        (int) block.getWIDTH(),
                        (int) block.getHEIGHT());
            }
            for (TextItem item : allText) {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(textStroke);
                g2d.drawString(item.getContent(),
                        item.getX(),
                        item.getY()
                );
            }
            for (TextBlockType.TextLine line : allLines) {
                ///logger.debug("TextLine: id={}", line.getID());

                g2d.setColor(Color.BLACK);
                g2d.setStroke(textStroke);
                g2d.drawRect((int) line.getHPOS(),
                        (int) line.getVPOS(),
                        (int) line.getWIDTH(),
                        (int) line.getHEIGHT());

                g2d.drawString(line.getID() != null ? line.getID() : "null",
                        (int) line.getHPOS(),
                        (int) line.getVPOS());
                ///logger.debug("LineContent: id={}, class={}", "", lineContent.getClass());

            }
            // Disposes of this graphics context and releases any system resources that it is using. 
            g2d.dispose();

            // Save as PNG
            String fileName = String.format("%s_%s.png", this.abbyyXml, page.getID());
            Path inputXml = Paths.get(this.abbyyXml);

            Path outputPng = Paths.get(this.outputFolder, inputXml.getFileName().toString() + "_" + page.getID() + ".png");
            Path outputText = Paths.get(this.outputFolder, inputXml.getFileName().toString() + "_" + page.getID() + ".csv");
            File file = outputPng.toFile();
            logger.info("Output={}", fileName);
            try {
                ImageIO.write(bufferedImage, "png", file);
                // Save as JPEG
            } catch (IOException ex) {
                logger.error("print", ex);
            }

            PrintWriter pw = new PrintWriter(outputText.toFile(), "utf-8");
            String separator = ",";
            pw.println("Text,Page,Block,Line,X,Y,Width,Height");
            for (TextItem item : allText) {
                pw.print(item.getContent());
                pw.print(separator);
                pw.print(item.getPage());
                pw.print(separator);
                pw.print(item.getBlock());
                pw.print(separator);
                pw.print(item.getLine());
                pw.print(separator);
                pw.print(item.getX());
                pw.print(separator);
                pw.print(item.getY());
                pw.print(separator);
                pw.print(item.getWidth());
                pw.print(separator);
                pw.print(item.getHeight());

                pw.println("");
            }
            pw.close();
            logger.info("Export Finish, Page={}, OutputPNG={}, outputText={}",
                    page.getID(),
                    outputPng.toAbsolutePath().toString(),
                    outputText.toAbsolutePath().toString());
        }

        // Constructs a BufferedImage of one of the predefined image types.
    }

    public void extractTextItem(String page, ComposedBlockType block, List<TextItem> allText, List<TextLine> allLines) {
        List<TextItem> results = new ArrayList<>();
        for (Object temp : block.getTextBlockOrIllustrationOrGraphicalElement()) {
            if (temp instanceof TextBlockType) {
                extractTextItem(page, (TextBlockType) temp, allText, allLines);
            } else if (temp instanceof ComposedBlockType) {
                extractTextItem(page, (ComposedBlockType) temp, allText, allLines);
            }
        }

        logger.info("extractTextItem: ComposedBlockType, page={}, block={}, items={}", page, block.getID(), results.size());
        allText.addAll(results);
    }

    public void extractTextItem(String page, TextBlockType block, List<TextItem> allText, List<TextLine> allLines) {
        List<TextItem> results = new ArrayList<>();

        List<TextBlockType.TextLine> lines = block.getTextLine();
        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            TextBlockType.TextLine line = lines.get(lineIndex);
            for (Object lineContent : line.getStringAndSP()) {
                if (lineContent instanceof ABBYY.SPType) {
                    ABBYY.SPType spItem = (ABBYY.SPType) lineContent;
                    ///logger.debug("spItem: id={}, class={}, index={}", line.getID(), line.getClass(), lineIndex);
                } else if (lineContent instanceof ABBYY.StringType) {
                    ABBYY.StringType stringItem = (ABBYY.StringType) lineContent;
                    logger.debug("StringItem: id={}, content={}, language={}", stringItem.getID(), stringItem.getCONTENT(), stringItem.getLANG());
                    TextItem item = new TextItem(stringItem.getID(),
                            page,
                            block.getID(),
                            lineIndex,
                            stringItem.getCONTENT(),
                            stringItem.getHPOS().intValue(),
                            stringItem.getVPOS().intValue() + stringItem.getHEIGHT().intValue() / 2,
                            stringItem.getWIDTH().intValue(),
                            stringItem.getHEIGHT().intValue()
                    );
                    results.add(item);
                }
            }
            line.setID(block.getID() + "_" + lineIndex);
            allLines.add(line);

        }
        logger.info("extractTextItem: TextBlockType, page={}, block={}, items={}", page, block.getID(), results.size());
        allText.addAll(results);
    }

}

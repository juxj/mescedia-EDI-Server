package org.mescedia.edi.converter.main;import org.mescedia.edi.converter.unEdifact2Xml.UnEDIfact2XmlConverter;import org.mescedia.edi.converter.xml2unEdifact.Xml2UnEDIfactConverter;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import java.io.IOException;import java.nio.charset.StandardCharsets;import java.nio.file.Files;import java.nio.file.Paths;import java.util.stream.Stream;public class EdifactConverter {    private static Logger logger = LoggerFactory.getLogger(EdifactConverter.class);    public static void main(String[] args) {        try {            int parameterCount = args.length;            logger.debug("parameters count: {}", parameterCount);            if (parameterCount < 3) {                logger.error("-----------------------------");                logger.error("cmd: java -jar JAR_FILE_NAME");                logger.error("parameters 1: action , options: 0 | 1,  0->edit2xml 1->xml2edi");                logger.error("parameters 2: edi file name with full path");                logger.error("parameters 3: xml file name with full path");                logger.error("---------- optional --------------");                logger.error("parameters 4: edi version , options: d97a | d07a");                logger.error("parameters 5: interchange controlling reference");                return;            }            String action = args[0];            String ediFileName = args[1];            String xmlFileName = args[2];            String ver = null;            String controlRef = null;            logger.debug("action: {}, ediFileName: {}, xmlFileName: {}", action, ediFileName, xmlFileName);            if (parameterCount > 3) {                ver = args[3];            }            if (parameterCount > 4 ) {                controlRef = args[4];            }            logger.debug("version: {}, controlRef: {}", ver, controlRef);            EdifactConverter converter = new EdifactConverter();            converter.run(Integer.valueOf(action), ediFileName, xmlFileName, ver, controlRef);        } catch (Exception e) {            logger.error("err: {}", e.getMessage());            e.printStackTrace();        }    }    private void run(int action, String ediFileName, String xmlFileName, String ver, String interchageControlRef) throws Exception {        switch (action) {            case 0: {                edi2xml(ediFileName, xmlFileName, ver);                break;            }            case 1: {                xml2edi(ediFileName, xmlFileName, interchageControlRef);                break;            }            default: {                logger.debug("action code error.");            }        }    }    public void edi2xml(String ediFileName, String xmlFileName, String ver) throws Exception {        logger.debug("action: edi to xml");        String text = readLineByLineJava8(ediFileName);        UnEDIfact2XmlConverter converter = new UnEDIfact2XmlConverter();        String xml = converter.convert2Xml(ver, text, 0);        wrireText2File(xmlFileName, xml);    }    public void xml2edi(String ediFileName, String xmlFileName, String interchangeControlRef) throws Exception {        logger.debug("edi file: {}, xml: {}", ediFileName, xmlFileName);        String text = readLineByLineJava8(xmlFileName);        Xml2UnEDIfactConverter converter = Xml2UnEDIfactConverter.getInstance();        String edi = converter.convertToUNEdifact(text, interchangeControlRef);        wrireText2File(ediFileName, edi);    }    private void wrireText2File(String fileName, String text) throws IOException {        Files.write(Paths.get(fileName), text.getBytes());    }    private String readLineByLineJava8(String filePath) {        StringBuilder contentBuilder = new StringBuilder();        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {            stream.forEach(s -> contentBuilder.append(s).append("\n"));        } catch (IOException e) {            e.printStackTrace();        }        return contentBuilder.toString();    }}
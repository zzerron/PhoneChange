package sample;

import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;

public class Model {

    Controller controller;
    Document document;
    String message;
    String path;

    public Model(Controller controller) {
        this.controller = controller;
    }

//    Метод для изменения поля Phone
    public void setText(String newPhone){
        if (document != null){
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            try {
                XPathExpression expr = xpath.compile("resources/string"+"[@name='prompt_phone']");
                NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
                Node node = nl.item(0);
                node.setTextContent(newPhone);
                writeDocument(document);
                message = "Сохранение прошло успешно!";
                controller.setMessage_field(message);
            } catch (XPathExpressionException e) {
                message = "Произощла ошибка при сохранении изменения";
                controller.setMessage_field(message);
            }
        }
        else{
            message = "Вы еще не выбрали документ";
            controller.setMessage_field(message);
        }
    }

//    Метод для нахождения необходимого значения
    public void getText(String path){
        document = getDocument(path);
        if (document != null){
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            try {
                XPathExpression expr = xpath.compile("resources/string"+"[@name='prompt_phone']");
                NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
                if (nl.getLength() > 1){
                    message = "Документ содержит нескольколько полей 'prompt_phone'";
                    controller.setMessage_field(message);
                    throw new XPathExpressionException("Документ содержит нескольколько полей 'prompt_phone'");
                }
                else if (nl.getLength() == 0){
                    message = "Такого поля нету в документе";
                    controller.setMessage_field(message);
                    throw new XPathExpressionException("Такого поля нету в документе");
                }
                else {
                    Node node = nl.item(0);
                    String phone = node.getTextContent();
                    controller.setPhone_field(phone);
                    message = "Вы можете изменить и сохранить значение";
                    controller.setMessage_field(message);
                }
            } catch (XPathExpressionException e) {
            }
        }
        else{
        }
    }

//    Метод для подключения к указанному документу
    private Document getDocument(String path){
        Document document = null;
        this.path = path;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(path);
        } catch (ParserConfigurationException e) {
            message = "Невозможно прочитать указанный файл";
        }  catch (IOException e) {
            message = "Данный файл не является '.xml'";
        } catch (SAXException e) {
            message = "Вы не указали путь к файлу";
        }

        controller.setMessage_field(message);
        return document;
    }

//    Записывает в файл изменения в документе
    private void writeDocument(Document document) {

        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        DOMBuilder domBuilder = new DOMBuilder();
        org.jdom2.Document document1 = domBuilder.build(document);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            xmlWriter.output(document1, fos);
        } catch (FileNotFoundException  e) {
            message = "Документ не найден, изменения не сохранены";
            controller.setMessage_field(message);
        } catch (IOException e) {
            message = "Произощла ошибка при сохранении изменения";
            controller.setMessage_field(message);
        }
    }
}

package com.github.zhangsiyao.FasterForge.ForgeBoot.Utils.Svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

public class SvgLoader {

    private Document document;

    public  SvgLoader(String url){
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = url;
        Document doc = impl.createDocument(svgNS, "svg", null);
        this.document=doc;
    }



    public  SvgLoader(File file){
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        try {
            document = f.createDocument(file.toURI().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSvgSize(int width,int height){
        Element svgRoot = document.getDocumentElement();
        svgRoot.setAttributeNS(null, "width", String.valueOf(width));
        svgRoot.setAttributeNS(null, "height", String.valueOf(height));
    }

    public Document getDocument() {
        return document;
    }
}

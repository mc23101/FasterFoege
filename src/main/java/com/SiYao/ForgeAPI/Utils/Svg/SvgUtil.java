package com.SiYao.ForgeAPI.Utils.Svg;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.renderer.ConcreteImageRendererFactory;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.gvt.renderer.ImageRendererFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGElement;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class SvgUtil {

    private static final boolean DEBUG = false;

    public static <T extends Node> String convertElemToSVG(T element) {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        StringWriter buffer = new StringWriter();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        try {
            transformer.transform(new DOMSource(element), new StreamResult(buffer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String elementStr = buffer.toString();
        return elementStr;
    }


    public static BufferedImage renderXMLToImage(String xmlContent, int width, int height) throws IOException {
        return renderXMLToImage(xmlContent, width, height, false, null, null);
    }

    public static BufferedImage renderXMLToImage(String xmlContent, int width, int height, boolean stretch, String idRegex, Color replacementColor) throws IOException {
        // the following is necessary so that batik knows how to resolve URI fragments
        // (#myLinearGradient). Otherwise the resolution fails and you cannot render.

        String uri = "file:/fake.svg";

        SAXSVGDocumentFactory df = new SAXSVGDocumentFactory("org.apache.xerces.parsers.SAXParser");
        SVGDocument document = df.createSVGDocument(uri, new StringReader(xmlContent));
        if(idRegex != null && replacementColor != null)
            replaceFill(document, idRegex, replacementColor);
        return renderToImage(document, width, height, stretch);
    }

    public static BufferedImage renderToImage(String uri, int width, int height) throws IOException {
        return renderToImage(uri, width, height, false, null, null);
    }

    public static BufferedImage renderToImage(String uri, int width, int height, boolean stretch, String idRegex, Color replacementColor) throws IOException {
        SAXSVGDocumentFactory df = new SAXSVGDocumentFactory("org.apache.xerces.parsers.SAXParser");
        SVGDocument document = df.createSVGDocument(uri);
        if(idRegex != null && replacementColor != null)
            replaceFill(document, idRegex, replacementColor);
        return renderToImage(document, width, height, stretch);
    }

    public static BufferedImage renderToImage(Document document, int width, int height){
        return renderToImage(document, width, height, false);
    }

    public static void replaceFill(Document document, String idRegex, Color color){
        String colorCode = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());

        if(DEBUG) System.out.println("color code: "+colorCode);

        NodeList children = document.getElementsByTagName("*");
        for(int i = 0; i < children.getLength(); i++){
            if(children.item(i) instanceof SVGElement){
                SVGElement element = (SVGElement) children.item(i);
                if(element.getId().matches(idRegex)){
                    if(DEBUG) System.out.println("child>>> "+element+", "+element.getId());
                    String style = element.getAttributeNS(null, "style");
                    style = style.replaceFirst("fill:#[a-zA-z0-9]+", "fill:"+colorCode);
                    if(DEBUG) System.out.println(style);
                    element.setAttributeNS(null, "style", style);
                }
            }
        }
    }

    public static BufferedImage renderToImage(Document document, int width, int height, boolean stretch){

        ImageRendererFactory rendererFactory;
        rendererFactory = new ConcreteImageRendererFactory();
        ImageRenderer renderer = rendererFactory.createStaticImageRenderer();

        GVTBuilder builder = new GVTBuilder();
        BridgeContext ctx = new BridgeContext(new UserAgentAdapter());
        ctx.setDynamicState(BridgeContext.STATIC);
        GraphicsNode rootNode = builder.build(ctx, document);

        renderer.setTree(rootNode);

        float docWidth  = (float) ctx.getDocumentSize().getWidth();
        float docHeight = (float) ctx.getDocumentSize().getHeight();

        float xscale = width/docWidth;
        float yscale = height/docHeight;
        if(!stretch){
            float scale = Math.min(xscale, yscale);
            xscale = scale;
            yscale = scale;
        }

        AffineTransform px  = AffineTransform.getScaleInstance(xscale, yscale);

        double tx = -0 + (width/xscale - docWidth)/2;
        double ty = -0 + (height/yscale - docHeight)/2;
        px.translate(tx, ty);
        //cgn.setViewingTransform(px);

        renderer.updateOffScreen(width, height);
        renderer.setTree(rootNode);
        renderer.setTransform(px);
        //renderer.clearOffScreen();
        renderer.repaint(new Rectangle(0, 0, width, height));

        return renderer.getOffScreen();

    }

}
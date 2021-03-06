/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.dashboard.displayer.chart;

import org.jboss.dashboard.displayer.*;
import org.jboss.dashboard.domain.DomainConfiguration;
import org.jboss.dashboard.domain.RangeConfiguration;

import java.io.PrintWriter;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.dashboard.export.ImportResults;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * This class it offers both Chart displayer XML parsing and formatting services.
 * <p>Below is a sample chart formatted as XML:<br><br>
 * <font size="-1">
 * &lt;barchartdisplayer&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;width&gt;300&lt;/width&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;height&gt;200&lt;/height&gt;<br>
 * &lt;/barchartdisplayer&gt;<br>
 * </font>
 */
@ApplicationScoped
public class ChartDisplayerXMLFormat extends AbstractDataDisplayerXMLFormat {

    public ChartDisplayerXMLFormat() {
        super();
    }

    protected void parseDisplayer(DataDisplayer dd, NodeList xmlNodes, ImportResults results) throws Exception {
        AbstractChartDisplayer displayer = (AbstractChartDisplayer) dd;
        for (int i = 0; i < xmlNodes.getLength(); i++) {
            Node item = xmlNodes.item(i);
            parseDisplayer(displayer, item, results);
        }
    }

    protected void parseDisplayer(AbstractChartDisplayer displayer, Node item, ImportResults results) throws Exception {
        // Domain.
        if (item.getNodeName().equals("domain") && item.hasChildNodes()) {
            NodeList domainNodes = item.getChildNodes();
            displayer.setDomainConfiguration(parseDomain(domainNodes));
        }
        // Range.
        else if (item.getNodeName().equals("range") && item.hasChildNodes()) {
            NodeList rangeNodes = item.getChildNodes();
            displayer.setRangeConfiguration(parseRange(rangeNodes));
        }
        else if (item.getNodeName().equals("type") && item.hasChildNodes()) {
            displayer.setType(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue()));
        }
        else if (item.getNodeName().equals("showlabelsxaxis") && item.hasChildNodes() ) {
            displayer.setShowLabelsXAxis(Boolean.parseBoolean(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("labelanglexaxis") && item.hasChildNodes() && displayer instanceof AbstractXAxisDisplayer) {
            AbstractXAxisDisplayer xAxisDisplayer = (AbstractXAxisDisplayer) displayer;
            xAxisDisplayer.setLabelAngleXAxis(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("showlinesarea") && item.hasChildNodes() && displayer instanceof AbstractXAxisDisplayer) {
            AbstractXAxisDisplayer xAxisDisplayer = (AbstractXAxisDisplayer) displayer;
            xAxisDisplayer.setShowLinesArea(Boolean.parseBoolean(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("intervalsortcriteria") && item.hasChildNodes()) {
            displayer.setIntervalsSortCriteria(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("intervalsortorder") && item.hasChildNodes()) {
            displayer.setIntervalsSortOrder(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("color") && item.hasChildNodes()) {
            displayer.setColor(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue()));
        }
        else if (item.getNodeName().equals("backgroundcolor") && item.hasChildNodes()) {
            displayer.setBackgroundColor(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue()));
        }
        else if (item.getNodeName().equals("width") && item.hasChildNodes()) {
            displayer.setWidth(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("height") && item.hasChildNodes()) {
            displayer.setHeight(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("showlegend") && item.hasChildNodes()) {
            displayer.setShowLegend(Boolean.valueOf(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())).booleanValue());
        }
        else if (item.getNodeName().equals("axisinteger") && item.hasChildNodes()) {
            displayer.setAxisInteger(Boolean.valueOf(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())).booleanValue());
        }
        else if (item.getNodeName().equals("legendanchor") && item.hasChildNodes()) {
            displayer.setLegendAnchor(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue()));
        }
        else if (item.getNodeName().equals("showtitle") && item.hasChildNodes()) {
            displayer.setShowTitle(Boolean.valueOf(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())).booleanValue());
        }
        else if (item.getNodeName().equals("align") && item.hasChildNodes()) {
            displayer.setGraphicAlign(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue()));
        }
        else if (item.getNodeName().equals("marginleft") && item.hasChildNodes()) {
            displayer.setMarginLeft(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("marginright") && item.hasChildNodes()) {
            displayer.setMarginRight(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("margintop") && item.hasChildNodes()) {
            displayer.setMarginTop(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
        else if (item.getNodeName().equals("marginbottom") && item.hasChildNodes()) {
            displayer.setMarginBottom(Integer.parseInt(StringEscapeUtils.unescapeXml(item.getFirstChild().getNodeValue())));
        }
    }

    protected void formatDisplayer(DataDisplayer displayer, PrintWriter out, int indent) throws Exception {
        try {
            AbstractChartDisplayer displayerToFormat = (AbstractChartDisplayer) displayer;

            // Format the domain.
            printIndent(out, indent++);
            out.println("<domain>");
            DomainConfiguration domainConfig = new DomainConfiguration(displayerToFormat.getDomainProperty());
            displayerToFormat.setDomainConfiguration(domainConfig);
            formatDomain(domainConfig, out, indent);
            printIndent(out, --indent);
            out.println("</domain>");

            // Format the range.
            printIndent(out, indent++);
            out.println("<range>");
            RangeConfiguration rangeConfig = new RangeConfiguration(displayerToFormat.getRangeProperty(), displayerToFormat.getRangeScalarFunction(), displayerToFormat.getUnitI18nMap());
            displayerToFormat.setRangeConfiguration(rangeConfig);
            formatRange(rangeConfig, out, indent);
            printIndent(out, --indent);
            out.println("</range>");

            printIndent(out, indent);
            out.print("<type>");
            out.print(StringEscapeUtils.escapeXml(displayerToFormat.getType()));
            out.println("</type>");

            if (displayerToFormat.getIntervalsSortOrder() != AbstractChartDisplayer.INTERVALS_SORT_ORDER_NONE) {
                printIndent(out, indent);
                out.print("<intervalsortcriteria>");
                out.print(StringEscapeUtils.escapeXml(String.valueOf(displayerToFormat.getIntervalsSortCriteria())));
                out.println("</intervalsortcriteria>");

                printIndent(out, indent);
                out.print("<intervalsortorder>");
                out.print(StringEscapeUtils.escapeXml(String.valueOf(displayerToFormat.getIntervalsSortOrder())));
                out.println("</intervalsortorder>");
            }

            printIndent(out, indent);
            out.print("<showlabelsxaxis>");
            out.print(displayerToFormat.isShowLabelsXAxis());
            out.println("</showlabelsxaxis>");


            // X-Axis based charts properties
            if (displayerToFormat instanceof AbstractXAxisDisplayer) {
                formatXAxisLabelsProperties(displayerToFormat, out, indent);
            }

            printIndent(out, indent);
            out.print("<color>");
            out.print(StringEscapeUtils.escapeXml(displayerToFormat.getColor()));
            out.println("</color>");

            printIndent(out, indent);
            out.print("<backgroundcolor>");
            out.print(StringEscapeUtils.escapeXml(displayerToFormat.getBackgroundColor()));
            out.println("</backgroundcolor>");

            printIndent(out, indent);
            out.print("<width>");
            out.print(StringEscapeUtils.escapeXml(Integer.toString(displayerToFormat.getWidth())));
            out.println("</width>");

            printIndent(out, indent);
            out.print("<height>");
            out.print(StringEscapeUtils.escapeXml(Integer.toString(displayerToFormat.getHeight())));
            out.println("</height>");

            printIndent(out, indent);
            out.print("<showlegend>");
            out.print(StringEscapeUtils.escapeXml(Boolean.toString(displayerToFormat.isShowLegend())));
            out.println("</showlegend>");

            printIndent(out, indent);
            out.print("<axisinteger>");
            out.print(StringEscapeUtils.escapeXml(Boolean.toString(displayerToFormat.isAxisInteger())));
            out.println("</axisinteger>");

            printIndent(out, indent);
            out.print("<legendanchor>");
            out.print(StringEscapeUtils.escapeXml(displayerToFormat.getLegendAnchor()));
            out.println("</legendanchor>");

            printIndent(out, indent);
            out.print("<showtitle>");
            out.print(StringEscapeUtils.escapeXml(Boolean.toString(displayerToFormat.isShowTitle())));
            out.println("</showtitle>");

            printIndent(out, indent);
            out.print("<align>");
            out.print(StringEscapeUtils.escapeXml(displayerToFormat.getGraphicAlign()));
            out.println("</align>");

            printIndent(out, indent);
            out.print("<marginleft>");
            out.print(StringEscapeUtils.escapeXml(Integer.toString(displayerToFormat.getMarginLeft())));
            out.println("</marginleft>");

            printIndent(out, indent);
            out.print("<marginright>");
            out.print(StringEscapeUtils.escapeXml(Integer.toString(displayerToFormat.getMarginRight())));
            out.println("</marginright>");

            printIndent(out, indent);
            out.print("<margintop>");
            out.print(StringEscapeUtils.escapeXml(Integer.toString(displayerToFormat.getMarginTop())));
            out.println("</margintop>");

            printIndent(out, indent);
            out.print("<marginbottom>");
            out.print(StringEscapeUtils.escapeXml(Integer.toString(displayerToFormat.getMarginBottom())));
            out.println("</marginbottom>");

        } catch (ClassCastException e) {
            throw new RuntimeException("Can not format non-chart displayers: " + displayer.getClass().getName());
        }
    }

    // X-Axis labels properties.

    public void formatXAxisLabelsProperties(AbstractChartDisplayer displayerToFormat, PrintWriter out, int indent) {
        AbstractXAxisDisplayer xAxisDisplayer = (AbstractXAxisDisplayer) displayerToFormat;

        printIndent(out, indent);
        out.print("<labelanglexaxis>");
        out.print(StringEscapeUtils.escapeXml(Integer.toString(xAxisDisplayer.getLabelAngleXAxis())));
        out.println("</labelanglexaxis>");

        printIndent(out, indent);
        out.print("<showlinesarea>");
        out.print(StringEscapeUtils.escapeXml(Boolean.toString(xAxisDisplayer.isShowLinesArea())));
        out.println("</showlinesarea>");

    }
}

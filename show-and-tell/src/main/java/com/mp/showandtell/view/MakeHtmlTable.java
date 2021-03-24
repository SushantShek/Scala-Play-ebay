package com.mp.showandtell.view;

import com.mp.showandtell.domain.CreditInput;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class MakeHtmlTable {
    static class HTMLStyle extends ToStringStyle {
        public HTMLStyle(){
            setFieldSeparator("</td></tr>"+ SystemUtils.LINE_SEPARATOR + "<tr><td>");

            setContentStart("<table>"+ SystemUtils.LINE_SEPARATOR +
                    "<thead><tr><th>Name</th><th>Address</th><th>Postcode</th><th>Phone</th><th>Credit Limit</th><th>Birthday</th></tr></thead>" +
                    "<tbody><tr><td>");

            setFieldNameValueSeparator("</td><td>");

            setContentEnd("</td></tr>"+ SystemUtils.LINE_SEPARATOR + "</tbody></table>");

            setArrayContentDetail(true);
            setUseShortClassName(true);
            setUseClassName(false);
            setUseIdentityHashCode(false);
        }

        @Override
        public void appendDetail(StringBuffer buffer, String fieldName, Object value) {
//            System.out.println(value.getClass().getName());
//            List<CreditInput> inputList = (List<CreditInput>) value;
//            for(CreditInput ci : inputList) {
                if (value.getClass().getName().startsWith("java.lang")) {
                    super.appendDetail(buffer, fieldName, value);
                } else {
                    buffer.append(ReflectionToStringBuilder.toString(value, this));
                }
            }
//        }

    }
    public static String MakeHtmlTable(Object object) {
//        List<CreditInput> inputList = (List<CreditInput>) object;
        return	ReflectionToStringBuilder.toString(object, new HTMLStyle());
    }
}

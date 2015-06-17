package de.nomagic.clientlib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataReply extends ServerReply
{
    private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass().getName());

    private String[] generalSection;
    private HashMap<String, String[]> sections = new HashMap<String, String[]>();

    public DataReply(String[] data, int offset)
    {
        String curSection = null;
        Vector<String> curData = new Vector<String>();
        for(int i = offset; i< data.length; i++)
        {
            String line = data[i];
            line = line.trim();
            if(line.startsWith("["))
            {
                // safe data
                if(null == curSection)
                {
                    log.trace("Found general Section");
                    generalSection = curData.toArray(new String[0]);
                }
                else
                {
                    log.trace("Found " + curSection + " Section");
                    sections.put(curSection, curData.toArray(new String[0]));
                }
                // start of new Section
                curSection = line.substring(1, line.length() -1);
                curData = new Vector<String>();
            }
            else
            {
                curData.add(line);
            }
        }
        // save last section
        if(null == curSection)
        {
            // last section was first section
            log.trace("Found general Section");
            generalSection = curData.toArray(new String[0]);
        }
        else
        {
            log.trace("Found " + curSection + " Section");
            sections.put(curSection, curData.toArray(new String[0]));
        }
    }

    public String[] getGeneralData()
    {
        return generalSection;
    }

    public String[] getSectionData(String SectionName)
    {
        return sections.get(SectionName);
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = sections.keySet().iterator();
        while(it.hasNext())
        {
            String curName = it.next();
            sb.append(curName);
        }
        return Arrays.toString(generalSection) + sb.toString();
    }

    public boolean getBooleanValueOf(String Setting)
    {
        return getBooleanValueOf(Setting, generalSection);
    }

    public boolean getBooleanValueOf(String Setting, String SectionName)
    {
        return getBooleanValueOf(Setting, sections.get(SectionName));
    }

    public static boolean getBooleanValueOf(String Setting, String[] values)
    {
        for(int i = 0; i < values.length; i++)
        {
            if(true == values[i].startsWith(Setting))
            {
                if(true == values[i].endsWith("true"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            // else uninteresting line
        }
        return false;
    }

    public String getStringValueOf(String Setting)
    {
        return getStringValueOf(Setting, generalSection);
    }

    public String getStringValueOf(String Setting, String SectionName)
    {
        return getStringValueOf(Setting, sections.get(SectionName));
    }

    public static String getStringValueOf(String Setting, String[] values)
    {
        for(int i = 0; i < values.length; i++)
        {
            if(true == values[i].startsWith(Setting))
            {
                return values[i].substring(values[i].indexOf(':') + 1).trim();
            }
            // else uninteresting line
        }
        return "";
    }
}

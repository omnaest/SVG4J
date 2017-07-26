/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.svg.utils;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Helper class for parsing and serializing xml content
 *
 * @see #parse(String, Class)
 * @see #serialize(Object)
 * @author Omnaest
 */
public class XMLHelper
{
	public static class ParseRuntimException extends RuntimeException
	{
		private static final long serialVersionUID = -2172248039344150351L;

		public ParseRuntimException(Exception e)
		{
			super("Exception when parsing xml", e);
		}
	}

	public static class SerializeRuntimException extends RuntimeException
	{
		private static final long serialVersionUID = -2172223439344150351L;

		public SerializeRuntimException(Exception e)
		{
			super("Exception serializing xml object", e);
		}

	}

	/**
	 * Parses the given xml string into the given {@link Class} type
	 *
	 * @param xml
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parse(String xml, Class<T> type)
	{
		T retval = null;

		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Reader reader = new StringReader(xml);
			retval = (T) unmarshaller.unmarshal(reader);
			reader.close();
		} catch (Exception e)
		{
			throw new ParseRuntimException(e);
		}

		return retval;
	}

	/**
	 * Serializes the given model object into xml string
	 *
	 * @param model
	 * @return
	 */
	public static String serialize(Object model)
	{
		String retval = null;

		try
		{
			StringWriter writer = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(model.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.marshal(model, writer);
			writer.close();
			retval = writer.toString();

		} catch (Exception e)
		{
			throw new SerializeRuntimException(e);
		}

		return retval;
	}

	@SuppressWarnings("unchecked")
	public static <T> T clone(T model)
	{
		return parse(serialize(model), (Class<T>) model.getClass());
	}
}

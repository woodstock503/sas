package sas.saccplus.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.codec.binary.Hex;

/**
 * Session Bean implementation class BeanUtil
 */
@Named
@RequestScoped
public class BeanUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1091100103562652905L;
	@Inject
	private SessionBean session;

	public String getMessage(String key){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = facesContext.getApplication().getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
		return bundle.getString(key);
	}

	public String getMessage(String key,String defaultMessage){
		String message = defaultMessage;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = facesContext.getApplication().getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
		try{
			message = bundle.getString(key);
		}catch(Exception ex){

		}
		return message;
	}

	public String encodeMD5(String password) throws NoSuchAlgorithmException{
		final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(password.getBytes());
		final String hexString = new String(Hex.encodeHex(messageDigest.digest()));
		return hexString;
	}


	public String convertNumber(BigDecimal number){
		DecimalFormat df = new DecimalFormat(session.getCompany().getDigitPattern());
		return df.format(number);
	}

	public String convertNumber(int number){
		DecimalFormat df = new DecimalFormat(session.getCompany().getDigitPattern());
		return df.format(number);
	}

	public String convertNumber(String number){
		DecimalFormat df = new DecimalFormat(session.getCompany().getDigitPattern());
		if(number==null){
			return df.format(new BigDecimal("0"));
		}
		if(number.trim().equals("")){
			return df.format(new BigDecimal("0"));
		}

		Number n = null;
		try {
			n = df.parse(number);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return df.format(n);
	}

	public Number convertStringToNumber(String number){
		DecimalFormat df = new DecimalFormat(session.getCompany().getDigitPattern());
		if(number==null){
			return 0.00;
		}
		if(number.trim().equals("")){
			return 0.00;
		}
		Number n = null;
		try {
			n = df.parse(number);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}

	public BigDecimal convertStringToBigDecimal(String number){
		DecimalFormat df = new DecimalFormat(session.getCompany().getDigitPattern());
		if(number==null){
			return new BigDecimal("0");
		}
		if(number.trim().equals("")){
			return new BigDecimal("0");
		}
		BigDecimal n = null;
		try {
			n = new BigDecimal(df.parse(number).toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}

	public int digitScale(){
		String []pattern = session.getCompany().getDigitPattern().split("\\.");
		return pattern[1].length();
	}

	public Object copyProperties(Object dest,Object orig) throws Exception{
		ConvertUtils.register(new DateConverter(null), Date.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		BeanUtils.copyProperties(dest, orig);
		return dest;
	}

}

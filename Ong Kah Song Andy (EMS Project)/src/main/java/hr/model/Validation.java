package hr.model;

import java.util.regex.Pattern;

public class Validation {
	public final static Pattern NUMBERS_ONLY = Pattern.compile("^[0-9]{1,10}$");
	public final static Pattern LETTERS_ONLY = Pattern.compile("^[ a-zA-Z]{1,30}$");
	public final static Pattern PHONE_NUMBER = Pattern.compile("^[0-9.]{1,12}$");
	public final static Pattern YEAR = Pattern.compile("^[0-9]{1,4}");
	public final static Pattern MONTH = Pattern.compile("(0?[1-9]|1[0-2])");
	public final static Pattern DAY = Pattern.compile("(0?[1-9]|[12]\\d|30|31)");
	public final static Pattern CURRENCY = Pattern.compile("^[0-9]{1,7}.?[0-9]{0,2}?$");
}

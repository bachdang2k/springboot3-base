package vivas.common;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppHelper {
    public static String genName(String separator, String... args) {
        StringBuffer sf = new StringBuffer();
        for (String s : args) {
            sf.append(s).append(separator);
        }
        String path = sf.toString();
        if (null != path && !path.isEmpty()) {
            path = path.substring(0, path.lastIndexOf(separator));
        }
        return path;
    }
    
    public static String generateCommonLangPassword() {
        /*
        int DEFAULT_PASSWORD_LENGTH = 8;
        String VALID_PW_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_";
        return RandomStringUtils.random(DEFAULT_PASSWORD_LENGTH, 0, VALID_PW_CHARS.length(), false,
                false, VALID_PW_CHARS.toCharArray(), new SecureRandom());
                
         */
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*_";
        return RandomStringUtils.random(2, 0, lowerChars.length(), false,
                        false, lowerChars.toCharArray(), new SecureRandom())
                .concat(RandomStringUtils.random(2, 0, specialChars.length(), false,
                        false, specialChars.toCharArray(), new SecureRandom()))
                .concat(RandomStringUtils.random(2, 0, upperChars.length(), false,
                        false, upperChars.toCharArray(), new SecureRandom()))
                .concat(RandomStringUtils.random(2, 0, numberChars.length(), false,
                        false, numberChars.toCharArray(), new SecureRandom()));
    }
    
    public static String generateCommonLangPassword1() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }
    
    public static <T> Stream<T> asStream(final Collection<T> collection) {
        return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
    }
    
    public static String response(int code, String message) {
        StringBuffer sf = new StringBuffer();
        sf.append("{");
        sf.append("\"code\"").append(":").append(code).append(",");
        sf.append("\"message\"").append(":\"").append(message).append("\"");
        sf.append("}");
        return sf.toString();
    }
    
    public static String responseXml(String name, int code, String message) {
        StringBuffer sf = new StringBuffer();
        sf.append("<RPLY name=").append("\"").append(name).append("\">");
        sf.append("<ERROR>").append(code).append("</ERROR>");
        sf.append("<ERROR_DESC>").append(message).append("</ERROR_DESC>");
        sf.append("</RPLY>");
        return sf.toString();
    }
    
    public static String responseJson(String name, int code, String message) {
        StringBuffer sf = new StringBuffer();
        sf.append("{");
        sf.append("\"RPLY\"").append(":");
        sf.append("{");
        sf.append("\"name\"").append(":\"").append(name).append("\"").append(",");
        sf.append("\"ERROR\"").append(":\"").append(code).append("\"").append(",");
        sf.append("\"ERROR_DESC\"").append(":\"").append(message).append("\"");
        sf.append("}");
        sf.append("}");
        return sf.toString();
    }
    
    public static String escape(String input) {
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll("'", "&apos;");
        input = input.replaceAll("\"", "&quot;");
        return input;
    }
    
    public static String deescape(String input) {
        input = input.replaceAll("&amp;", "&");
        input = input.replaceAll("&gt;", ">");
        input = input.replaceAll("&lt;", "<");
        input = input.replaceAll("&apos;", "'");
        input = input.replaceAll("&quot;", "\"");
        input = input.replaceAll("&nbsp;", " ");
        return input;
    }
    
    public static String telcoName(int telcoId) {
        String telcoName = null;
        switch (telcoId) {
            case 1:
                telcoName = "Vinaphone";
                break;
            case 2:
                telcoName = "Mobifone";
                break;
            case 3:
                telcoName = "Viettel";
                break;
            case 4:
                telcoName = "Gtel";
                break;
            case 5:
                telcoName = "Vietnammobile";
                break;
        }
        return telcoName;
    }
    
    public static Throwable getrootcause(Exception e) {
        Optional<Throwable> rootCause = Stream.iterate(e, Throwable::getCause)
                .filter(element -> element.getCause() == null)
                .findFirst();
        return rootCause.orElse(null);
    }
    
    public static String duration(LocalDateTime before, LocalDateTime after) {
        return (Duration.between(before, after).getSeconds() > 0 ?
                Duration.between(before, after).getSeconds() + " (s)" :
                TimeUnit.MILLISECONDS.convert(Duration.between(before, after).getNano(), TimeUnit.NANOSECONDS) +
                        " (ms)");
    }
    
    public static <T> T nvl(T a, T b) {
        return (null == a) ? b : a;
    }
    
    public static boolean isEmailValid(String email) {
        String x = "^[^@,:; \\t\\r\\n]+@[^@,:; \\t\\r\\n]+\\.[^@,:; \\t\\r\\n]+$";
        final Pattern EMAIL_REGEX = Pattern.compile(
                x, Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    /*
    public static Data data(Integer pageindex, Integer pagesize, Long totalRecord) {
        Data data = new Data();
        Paging pg = new Paging();
        pg.setCurrent(pageindex);
        pg.setSize(pagesize);
        pg.setTotalRecords(totalRecord);
        Long totalPage = 0l;
        if (totalRecord % pagesize == 0) {
            totalPage = totalRecord / pagesize;
        } else {
            totalPage = totalRecord / pagesize + 1;
        }
        pg.setTotalPages(totalPage.intValue());
        data.setPaging(pg);
        return data;
    }
    */
}

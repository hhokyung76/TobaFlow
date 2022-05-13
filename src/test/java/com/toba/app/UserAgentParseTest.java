package com.toba.app;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentParseTest {
   // private final static Pattern UUID_REGEX_PATTERN =
//            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
    //        Pattern.compile("(^[da-f]{8}(?:-[da-f]{4}){3}-[da-f]{12}$)");

    public static final String UUID_V4_STRING =
//            "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}";
             "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
       //         "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
    /**
     *
     * Regular expression to match a Version 4 Universally Unique Identifier (UUID), in a
     * case-insensitive fashion.
     */
    public static final Pattern UUID_REGEX_PATTERN = Pattern.compile(UUID_V4_STRING);
    public static boolean isValidUUID(String str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }

    public static void main(String[] args) {

        /*
        Format 8-4-4-4-12
         */
        //String userAgentString = "Delfino-StarBank/G6.1.99|Android 12|SM-G977N|F8C56D59F7A1F96B|06754b37-56fc-47ba-897f-10ca1ad1ab11";
//        String userAgentString = "Delfino-StarBank/G6.1.99|Android 12|SM-G977N|F8C56D59F7A1F96B|00000000-0000-0000-0000-000000000000";
        String userAgentString = "Delfino-StarBank/G6.1.99|Android 12|SM-G977N|F8C56D59F7A1F96B|AAAAAAAA-AAAA-BBBB-FFFF-CCCCCCCCCCCC";

//        String userAgentString5 = "Delfino-StarBank/G6.1.99|Android 12|SM-G977N|F8C56D59F7A1F96B|06754b37-56fc-47bd-897f-10ca1ad1abe1";

        List<String> userAgentStringSplitBy2 = Arrays.asList(userAgentString.split("\\|"));

        for (int ii = 0; ii < userAgentStringSplitBy2.size(); ii++) {
            System.out.println("str["+ii+"] => "+userAgentStringSplitBy2.get(ii));
        }

        String checkTargetId = userAgentStringSplitBy2.get(4);

        boolean tempCheck = isValidUUID(checkTargetId);
        System.out.println("tempCheck: "+tempCheck);

//        UUID temp3 = UUID.randomUUID();
//
//        try {
//            UUID.fromString(checkTargetId);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("checkTargetId: "+checkTargetId);
//        System.out.println("checkTargetId to UUID: "+temp3.toString());

//        UUID temp = UUID.randomUUID();
//        String uuidTemp = temp.toString();
//        System.out.println("uuidTemp: "+uuidTemp);
//        Matcher match = UUID_V4.matcher(checkTargetId);
//        System.out.println("match: "+match.find());
//
//        int matchCount = 0;
//        while (match.find()) {
//            System.out.println(matchCount + " : " + match.group());
//            matchCount++;
//        }
//        System.out.println("총 개수 : " + matchCount);

        String madid = "";
        UUID transToUUID = UUID.randomUUID();
        try {
            transToUUID = UUID.fromString(checkTargetId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("checkTargetId: " + checkTargetId);
        //System.out.println("checkTargetId to UUID: " + transToUUID.toString());
        System.out.println("transToUUID :"+transToUUID +" checkTargetId: "+checkTargetId);
        if (checkTargetId.equals(transToUUID.toString())) {
            System.out.println("true checkTargetId: "+checkTargetId);
            madid = checkTargetId;
        }else {
            System.out.println("false not match madid: "+checkTargetId+" t:=> "+transToUUID.toString());
        }
    }
}

public class CheckThreads implements ChannelStats {
    public static int checking(String StationName) {
        int stat;
        char ch = StationName.charAt(8);
        stat = Integer.parseInt(String.valueOf(ch));
        return (stat);
    }
}
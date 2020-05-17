import java.util.Scanner;

interface ChannelStats {
    int FREE = 0;
    int INUSE = 1;
}

class CSMACD implements ChannelStats {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        NewThread.ChannelStatus = FREE;
        System.out.println("Enter number of stations");
        int StatNum;
        while (true) {
            StatNum = sc.nextInt();
            if (StatNum < 6) {
                System.out.println("Minimum number of stations is six, please re-enter!");
                continue;
            } else
                break;
        }
        NewThread ObjArr[] = new NewThread[StatNum + 1];
        int FrameArray[] = new int[StatNum + 1];
        for (int i = 1; i <= StatNum; i++) {
            System.out.println("Enter number of frames for Station " + i);
            FrameArray[i] = sc.nextInt();
        }

        for (int i = 1; i <= StatNum; i++)

            ObjArr[i] = new NewThread("Station " + Integer.toString(i), FrameArray[i]);

        try {
            for (int i = 1; i <= StatNum; i++)
                ObjArr[i].t.join();
        } catch (InterruptedException e) {
            System.out.println("Main Thread Interrupted");
        }
        System.out.println("Transmission completed.");
    }
}
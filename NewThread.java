import java.util.concurrent.atomic.*;

class NewThread implements Runnable, ChannelStats {
    String StationNumber;
    Thread t;
    static int distance, stat = 0, frame;
    static int ChannelStatus;
    int FrameNo, MaxFrameNo;
    private AtomicBoolean SuccTrans;
    static int tfr = 50;
    private int NumberOfAttempts;

    NewThread(String threadname, int MaxFrameNo) {
        StationNumber = threadname;
        t = new Thread(this, StationNumber);
        FrameNo = 1;
        this.MaxFrameNo = MaxFrameNo;
        SuccTrans = new AtomicBoolean();
        t.start();

    }

    public void run() {
        while (!SuccTrans.get()) {
            this.NumberOfAttempts++;
            while (FrameNo <= MaxFrameNo) {
                if (this.NumberOfAttempts < 15) {

                    if (ChannelStatus == INUSE) {

                        System.out.println(StationNumber + " is using 1-Persistent sensing, channel is busy");
                        synchronized (this) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                System.out.println(("Interrupt"));
                            }
                        }
                    } else {
                        System.out.println(StationNumber + " is trying to transmit frame number : " + FrameNo);

                        if (ChannelStatus == FREE && distance == 0) {
                            stat = CheckThreads.checking(Thread.currentThread().getName());
                            frame = this.FrameNo;
                            ChannelStatus = INUSE;
                            for (; distance < 9000000; distance++)
                                for (int i = 0; i < 1000; i++)
                                    ;

                            System.out.println(StationNumber + " frame " + FrameNo + " is successful");

                            synchronized (this) {
                                try {
                                    notifyAll();
                                } catch (Exception e) {
                                    System.out.println("Exception caught at notifyAll function");
                                }
                            }
                            SuccTrans.set(true);
                            FrameNo++;
                            distance = 0;
                            ChannelStatus = FREE;
                        } else {// Collision has occurred System.out.println("Collision for frame " + FrameNo +
                                // " of " + StationNumber + " and frame " + frame + " of Station " + stat);

                            System.out.println("Retransmitting Station " + stat + "'s frame " + frame);
                            SuccTrans.set(false);
                            ChannelStatus = FREE;
                        }

                    }

                } else {
                    SuccTrans.set(true);
                    System.out.println("Too many attempts for frame " + FrameNo + " of " + StationNumber
                            + ". Transmission stopped");
                }

            }
        }
    }
}
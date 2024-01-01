import java.awt.EventQueue;


class MyProg {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                TetrisFrame frame = new TetrisFrame();
            }
        });
    }
}
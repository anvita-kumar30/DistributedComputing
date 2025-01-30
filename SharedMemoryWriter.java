import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.RandomAccessFile;
import java.util.concurrent.Semaphore;

public class SharedMemoryWriter {
    private static final String FILE_NAME = "shared_memory.bin";
    private static final int SIZE = 1024;
    private static final Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) {
        try {
            RandomAccessFile memoryFile = new RandomAccessFile(FILE_NAME, "rw");
            FileChannel channel = memoryFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(SIZE);
            
            // Write to shared memory
            System.out.print("Enter a message: ");
            byte[] message = new byte[SIZE];
            System.in.read(message);
            buffer.put(message);
            buffer.flip();
            channel.write(buffer);
            
            // Signal the reader process
            semaphore.release();
            
            System.out.println("Message written to shared memory. Run reader process to read it.");
            
            // Cleanup
            channel.close();
            memoryFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

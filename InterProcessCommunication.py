import mmap
import os
import sys
import threading

FILE_NAME = "shared_memory.bin"
SIZE = 1024
semaphore = threading.Semaphore(0)

def write_to_shared_memory():
    with open(FILE_NAME, "wb") as f:
        f.write(b"\x00" * SIZE)  # Initialize file with empty bytes
    
    with open(FILE_NAME, "r+b") as f:
        mm = mmap.mmap(f.fileno(), SIZE)
        
        message = input("Enter a message: ").encode()
        mm.seek(0)
        mm.write(message)
        
        # Signal the reader process
        semaphore.release()
        
        print("Message written to shared memory. Run reader process to read it.")
        
        mm.close()

if __name__ == "__main__":
    write_to_shared_memory()
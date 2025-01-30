import socket
import struct

MULTICAST_GROUP = "230.0.0.1"
PORT = 5000

def send_multicast():
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    ttl = struct.pack('b', 1)
    sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, ttl)
    
    print("Enter messages to send (type 'exit' to quit):")
    while True:
        message = input()
        if message.lower() == 'exit':
            break
        sock.sendto(message.encode(), (MULTICAST_GROUP, PORT))
        print("Message sent:", message)
    
    sock.close()

if __name__ == "__main__":
    send_multicast()
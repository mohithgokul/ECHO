# ECHO
ECHO: Efficient Communication Hub using Object Streams is a Java-based LAN chat application built using socket programming and file handling. It supports multi-client communication, friend-only chats, conversation-based messaging, offline message storage, and a clean terminal UI. No internet required, just pure networking.

A lightweight, Java-based LAN chat application for pure terminal communication.
No internet. No cloud. Just machines talking to machines.

✨ Features

🔗 Multi-client communication over LAN

👥 Friend-only chats for controlled messaging

💬 Conversation-based chat system

📁 Offline message storage using file handling

🖥️ Clean and minimal terminal UI

⚡ Real-time messaging via Java Object Streams

🌐 No internet required. Works entirely on local networks

🛠️ Technologies Used

Java

Socket Programming

ObjectInputStream & ObjectOutputStream

File Handling

🚀 How It Works

A server runs on one machine within the LAN.

Multiple clients connect to the server using IP address and port.

Messages are sent using Java Object Streams.

Conversations and offline messages are stored using file handling.

When a user reconnects, missed messages can be retrieved.

▶️ How to Run the Application

✅ Prerequisites

Java JDK 8 or above

All devices must be on the same LAN / Wi-Fi network

🖥️ Step 1: Start the Server

Open a terminal on the server machine

Navigate to the project root directory:

cd chatapp


Compile server files:

javac server/*.java


Run the server:

java server.ChatServer


Keep this terminal running
The server must stay active for clients to connect.

🌐 Step 2: Find Server IP Address

On the server machine:

Windows

ipconfig


Linux / macOS

ifconfig


Note the IPv4 address (example: 192.168.1.25).

💻 Step 3: Run the Client (On Any Device)

Open a terminal on the client machine

Navigate to the project root:

cd chatapp

Compile the client:

javac client/ChatClient.java

Run the client:

java client.ChatClient

🔐 Step 4: Login

When prompted:

Enter the server IP address

Enter the server port (default: 5000)

Enter a username

If the username is new, it will be created automatically.

📋 Step 5: Chat Dashboard

After login, you will see a chat dashboard listing your friends:

Friends with new messages are marked

Select a friend to open the chat

Conversations show messages clearly as:

[friend] message
[you] message

💬 Step 6: Chatting

Type messages to send them instantly

Messages are saved on the server

Friends can read messages even if they were offline

Type /back to return to the dashboard

📴 Offline Messaging Support

Sender must be online

Receiver can be offline

Messages are stored and delivered when the receiver logs in

🛑 Step 7: Logout

From the dashboard:

Choose the logout option

Client disconnects safely from the server

⚠️ Important Notes

Internet connection is NOT required

All devices must be on the same network

Server must be running before clients connect

Works on both same-device and multi-device setups

📁 Offline Message Storage

Messages sent to offline users are saved locally using file handling.

Stored messages are delivered when the user reconnects.

No database required. Lightweight and fast.

🔒 Networking Scope

Works only within the same LAN / Wi-Fi

No external servers

No internet dependency

No third-party APIs

This makes ECHO ideal for:

College labs

Hostels

Offices

Secure local environments

🧪 Sample Use Case

Start the server in a lab.

Students connect using their terminal.

Chat only with approved friends.

Disconnect and reconnect without losing messages.

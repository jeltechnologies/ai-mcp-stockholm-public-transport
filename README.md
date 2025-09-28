# Ask ChatGTP "What is the next bus from Erikslund to Kungsbroplan?"
![slbus](https://github.com/user-attachments/assets/31bac689-3e99-4fc9-bab9-74e4d3bab13e)

<sup>*Image by by AleWi - Own work, CC BY-SA 4.0, https://commons.wikimedia.org/w/index.php?curid=131932539*</sup>

## Get the answer is in the chat, in real time! 
![chatting](https://github.com/user-attachments/assets/0bc9a190-0b1a-4e2e-adba-00db1e2ff092)

# About this tool
The tool provides AI agents access to real-time Stockholms public transport data. 
The tool gets it data from SL, the Stockholm public transport company. 

Functions:
- Translation of the rich data provided by SL APIs, to a human-like format that AI chatbots understand.
- Exposure of the API as Model Context Protocol (MCP) server.
- OpenAI support for Open WebUI, with the preconfigured MCPO proxy from Open WebU (https://github.com/open-webui/mcpo)

# Why this tool? 
The Model Context Protocol (MCP) is a crucial enabler for agentic AI, often referred to as the "USB-C port" for AI applications. With MCP, AI applications like Claude or ChatGPT can access key information and perform tasks. I created this tool to understand how MCP works, its possibilities, and limitations. 

## My learnings 
Getting started with the Model Context Protocol in modern frameworks like Spring is incredibly easy. It's a "plug-and-play" solution that works out of the box. 
However, there are fundamental difficulties associated with the Model Context Protocol. The non-deterministic behavior of chatbots and the need to tailor the API for the chatbot, not the system, can be challenging. 

### AI acts like a cat, while REST APIs are for dogs 
In traditional APIs, clients perform the same requests and use responses in the same way, behaving in a binary manner. However, chatbots act more like cats - unpredictable and prone to random behavior. You need to persuade chatbots to use the tool with clear descriptions, but even then, there's no guarantee how they'll process information. 

### User versus system view 
I've noticed similarities between the challenges faced in industrial software engineering, particularly in telecommunications. Software engineers often have a "system view," understanding the system from inside out and wanting to maintain maximum flexibility. This can lead to overly complex configuration files with many parameters. REST APIs tend to have this problem as well. The system view is great for machines, but people get drowned in information.

Users have a "user view" - they just want to perform tasks efficiently. A plethora of options can overwhelm them, making it difficult for them to configure systems without breaking them. 
The solution lies in traditional usability design principles: 
1.	Understand the tasks that users need to perform.
2.	Collect only the data needed for those tasks (ignore the system-level configuration in this phase).
3.	Simplify screens and layouts to minimize clicks, scrolling, and data entry.
4.	Review with users before coding and transforming user-view into system-view by applying default configurations.
5.	And finally, use the latest Javascript fanciest framework to code your app. Or use JQuery, users won't care.

Developing an Model Context Protocol server requires a similar approach: forget about available data; design the Model Context Protocol server for the chatbot as if it were human. What tasks does it need to perform? Can we make it easier? Passing too much data, wrongly formatted, or not enough data increases risks that chatbots "break data" by hallucination. Tweaking that will take most time. 

### My vision on MCP going forward 
The Model Context Protocol has the potential to revolutionize agentic AI by focusing on usability for chatbots. But we must focus on the needs of the bot when we design Model Context Protocol servers. This goes against the nature of most software engineers. We are used to deterministic systems, where things are testable because they always behave the same. This approach has lead to high quality of horrible user interfaces, that pass all automatic tests. many horrible user interfaces. But with the user need as afterthough, many user interfaces are useless, in practical reality. We must now fall in the same trap with AI bots. Chatbots act randomly "how they feel for it" on what they ask for, but also on how what they process the information. Almost like humans!  

# Enough ranting already, let's get started!
All you need a Linux box with Docker, for example in the cloud or on a homeserver. 

## Installation instructions
- Get a Linux box with Docker installed.
- Clone this repository
- Edit file file `config.json` in directory `docker/mcpo-config`. Change the IP address to the IP address of your Linux box.  
```
{
  "mcpServers": {
	"stockholm-public-transport": {
      "type": "sse",
      "url": "http://<your-ip-here>:18107/api/v1/sse"
    }
  }
}
```
- Start the containers by executing the command `docker compose up`
- This will start a Spring application in Java as well as MCPO proxy. MCPO is a proxy from MCP to OpenAI API.
- Keep the docker running in the background to observe the logs.
- Optionally, you can also start the container with
  - `docker compose up -d` and then
  - `docker compose logs -f`

### Configuring the chatbot
MCP is an open standard, the tool thefore works with Open WebUI, and may also work with other AI tools like Claude or ChatGTP. 

#### Configuration of OpenWebUI 
This has been tested in Open WebUI v0.6.30.
- Open OpenWebUI as administrator, click on the account, choose Admin Panel, Settings, External Tools
- Click "Add Connection" 
- Use URL `http://<your-ip-here>:18108/stockholm-public-transport` and change the IP to the IP of the Linux box. 
- Choose a name and description. A good name and description way purseude the AI bot to use the tool.
![editconnectionopenwebui](https://github.com/user-attachments/assets/a76e47c4-96bf-481e-9b4a-bfc6eb6d9f27)
- Add the tool to models, so they are available in all chats.
![addtomodel](https://github.com/user-attachments/assets/06a8390a-4fe2-4f8e-af5c-2f8767ef1bab)











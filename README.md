# Ask ChatGTP "What is the next bus?"
![slbus](https://github.com/user-attachments/assets/31bac689-3e99-4fc9-bab9-74e4d3bab13e)

<sup>*Image by by AleWi - Own work, CC BY-SA 4.0, https://commons.wikimedia.org/w/index.php?curid=131932539*</sup>

## Answers by the AI bot 
![chatting](https://github.com/user-attachments/assets/0bc9a190-0b1a-4e2e-adba-00db1e2ff092)
<sup>*AI chatbot in Open Web UI*</sup> 

# About this tool
The tool provides AI agents access to real-time Stockholms public transport data. 

The tool gets its data from SL, the Stockholm public transport company. 

Functions:
- Translation of SL's data model, to a format that AI chatbots understand, basically by simplifying data to essential information.
- Supporting this as Model Context Protocol (MCP) server.
- Support for Open WebUI, with the preconfigured MCPO proxy from Open WebU (https://github.com/open-webui/mcpo)

# Why this tool? 
The Model Context Protocol (MCP) is a crucial enabler for agentic AI, often referred to as the "USB-C port" for AI applications. With MCP, AI applications like Claude or ChatGPT can access key information and perform tasks.  I created this tool to understand how MCP works, its possibilities, and limitations.

## My learnings 
Getting started with the Model Context Protocol in modern frameworks like Spring is incredibly easy. It's a "plug-and-play" solution that works out of the box. 

Tailoring the server for AI bots takes most times, because of the fundamental difficulties associated with the Model Context Protocol, like
- he need to tailor the API for the chatbot 
- The non-deterministic behavior of chatbots

### AI acts like a cat, while REST APIs are for dogs 
In traditional APIs, clients perform the same requests and use responses in the same way. AI chatbots act more like cats - unpredictable and prone to random behavior. You need to persuade chatbots to use the tool with clear descriptions, but even then, there's no guarantee how they'll process information. 

While working with the AI bots, I've noticed similarities to usability in industrial software engineering, particularly in telecommunications. Engineers see the system from the inside out. Without guidance they often like to expose all options to to maintain maximum flexibility. This can lead to overly complex configuration files with many parameters. REST APIs tend to have this problem as well. And this reflects also in the SL REST interface. This is great for machines, but users get drowned in information. Users just want to perform tasks efficiently. A plethora of options can overwhelm them, making it difficult for them to configure systems without breaking them. 

The solution lies in traditional usability design principles: 
1.	Understand the tasks that users need to perform.
2.	Collect only the data needed for those tasks (ignore the system-level configuration in this phase).
3.	Simplify screens and layouts to minimize clicks, scrolling, and data entry.
4.	Review with users before coding and transforming user-view into system-view by applying default configurations.
5.	And finally, code the screens in a Javascript framework of choice.

Developing an Model Context Protocol server requires a similar approach: forget about available data; design the Model Context Protocol server for the chatbot as if it were human. This means understanding things like, what tasks does it need to perform? Can we make it easier? Passing too much data, wrongly formatted, or not enough data increases risks that chatbots "break data" by hallucination. 

Tweaking that will take most time. 

### My vision on MCP going forward 
The Model Context Protocol has the potential to revolutionize agentic AI, and for it to work we need to focus on the needs of the AI bots. This goes against the nature of most software engineers. We are used to deterministic systems, where things are testable because they always behave the same. 

Developing human interfaces without usability has lead to high quality of horrible user interfaces. These are high quality, because thet pass all automatic tests. many horrible user interfaces. But with the user need as afterthought, much of these user interfaces are useless, in practical reality. We must now fall in the same trap with AI bots. 

Chatbots act human-like randomly "how they feel for it / what is fashionable / what correlates". Almost like humans!  

# Enough ranting already, let's get started!
To get this project running you will need a Linux box with Docker, for example in the cloud or on a homeserver. 

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
- Open OpenWebUI as administrator, click on the account, choose Admin Panel, Settings, External Tools
- Click "Add Connection" 
- Use URL `http://<your-ip-here>:18108/stockholm-public-transport` and change the IP to the IP of the Linux box. 
- Choose a name and description. A good name and description way purseude the AI bot to use the tool.
![editconnectionopenwebui](https://github.com/user-attachments/assets/a76e47c4-96bf-481e-9b4a-bfc6eb6d9f27)
- Add the tool to models, so they are available in all chats.
![addtomodel](https://github.com/user-attachments/assets/06a8390a-4fe2-4f8e-af5c-2f8767ef1bab)











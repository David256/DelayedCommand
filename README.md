# DelayedCommand (Continued version)
Game: Minecraft 1.20.2<br/>
Server: Spigot (Bukkit) / Paper<br/>
Version: 1.0

# Description
This plugin allows you to tell the server to perform a command after a given amount of seconds. It's also possible to repeat the command a certain amount of times or have it repeat infinitely.

# Usage
`/dcmd [once/repeat/infinite/cancel] [...]`

Example: `/dcmd once [seconds] [command]`<br/>
Example: `/dcmd repeat [how many times] [seconds] [command]`<br/>
Example: `/dcmd infinite [seconds] [command]`<br/>
Example: `/dcmd cancel [id]*`<br/>
<br/>

> *The id is given to you in chat when you setup a command

# Examples
`/dcmd once 10 time set day`<br/>
`/dcmd repeat 3 10 /time set day`<br/>
`/dcmd infinite 60 time set day`<br/>
`/dcmd cancel 25`<br/>

# Example use cases
- You want the server to shutdown after a certain amount of time, but you're not able to stay online to do it yourself.
- You are creating an arena event where waves of mobs should spawn after a certain amount of time. This would allow you to time function files execution through a command block.

# Permissions
delayedcommand.\*<br/>
delayedcommand.dcmd

# 🕸️ SpiderSense

![Static Badge](https://img.shields.io/badge/Version-v1.0-blue?color=red)

**SpiderSense** is a Minecraft Bukkit/Spigot plugin tailored for BoxPvP servers and other PvP-focused game modes where managing the presence of cobwebs is crucial. Cobwebs can be used strategically in PvP environments, but they can also clutter the game world and cause performance issues if not managed properly.

# 📦 Features
- **Automatic Removal of Cobwebs:** Cobwebs placed by players are automatically removed after a specified delay, ensuring they do not stay in the game world longer than necessary.
- **Configurable Timer:** Set the delay before cobwebs are removed in seconds to suit the dynamics of your server. For example, a 5-minute delay can help balance gameplay without letting cobwebs linger too long.
- **Player Notifications:** Players receive a notification message when their cobwebs are removed, keeping them informed about the plugin's actions.
- **Server Restart Handling:** On server restart or shutdown, all cobwebs are automatically cleared, maintaining a clean and fair playing environment.

# ⚙️ Configuration
Customize the plugin to fit your server’s needs with the following settings in `config.yml`:
```yaml
#########################################################################################################
#    Plugin now supports full hex color codes.                                                          #
#    You can use https://www.birdflop.com/resources/rgb/ to create your colors                          #
#    Developer; padrewin || Cold Development                                                            #
#    GitHub; https://github.com/padrewin || https://github.com/Cold-Development                         #
#    Links: https://colddev.dev || https://discord.colddev.dev                                          #
#########################################################################################################

# Messages prefix:
plugin-prefix: "&8「&#003254Sp&#18476Cid&#305C83er&#49709BSe&#6185B2ns&#6185B2e&8」&7» "

# Messages shown in chat:
removed-message: "&7Your placed cobwebs have been &cremoved&7!"
reload-message: "&7Config &areloaded&7."
no-permission-message: "&cYou don't have permission to do that."
shutdown-message: "&cCobwebs have been removed because the server is restarting."

# This setting defines the delay before cobweb blocks are automatically removed after being placed.
# The value is in seconds. For example, if set to 300, cobwebs placed will be removed after 5 minutes.
cobweb-timer: 300
```

# 🛠️ Installation
1. Download the latest version of SpiderSense from [Spigot](https://www.spigotmc.org/resources/spidersense.118609/).
2. Place the JAR file into your server's `plugins` directory.
3. Restart your server to generate the configuration file.
4. Edit `config.yml` to adjust settings as needed.
5. Reload or restart the server to apply the new configuration.

# 💻 Commands
- `/spidersense reload`: Reloads the plugin configuration.

# ⚠️ Permissions
- `spidersense.admin`: Total access to the plugin.

# 🛡️ Support
For any issues or questions, please open an issue on the GitHub Issues page.

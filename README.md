# vTarla - Advanced Minecraft Farm Plugin

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=java)](https://www.java.com/)
[![Paper](https://img.shields.io/badge/Paper-1.21-blue?style=flat-square&logo=minecraft)](https://papermc.io/)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)
[![GitHub Release](https://img.shields.io/github/release/Vixians/vTarla?style=flat-square)](https://github.com/Vixians/vTarla/releases)

## 🌾 About vTarla

vTarla is a professional-grade farming plugin for Paper Minecraft servers that transforms your farm world into a profitable virtual economy. Players break crops to earn virtual coins with a fully customizable multiplier system, dynamic marketplace, and global discount management.

### ⭐ Key Features

- **Async-First Architecture** - Zero TPS impact with fully asynchronous operations
- **Virtual Coin System** - Earn coins by breaking crops in designated farm world
- **Smart Multiplier System** - Permission-based tiers (Bronze 1.5x → Emerald 10x)
- **Dynamic Market** - Beautiful GUI-based marketplace with custom items and commands
- **Global Discounts** - Admin-controlled discount system for seasonal events
- **SQLite Database** - Persistent data storage with automatic backups
- **ActionBar & BossBar** - Real-time player statistics display
- **Reload Safe** - Zero data loss on `/tarla reload`
- **Fully Customizable** - Every message, color, and setting in config.yml
- **Comprehensive API** - External plugins can interact with vTarla
- **Event System** - Custom events for developer integration

## 🚀 Quick Start

### Installation

1. Download latest JAR from [releases](https://github.com/Vixians/vTarla/releases)
2. Place in `plugins/` folder
3. Restart server
4. Configure `plugins/vTarla/config.yml`
5. Run `/tarla reload`

### Basic Commands

```
/tarla stats          - View your statistics
/tarla market         - Open marketplace
/tarla multiplier     - View multiplier tiers
/taladiscount set 20  - Set 20% global discount (Admin)
```

## 📖 Documentation

- [Installation Guide](INSTALLATION.md)
- [Configuration Guide](CONFIGURATION.md)
- [API Reference](API.md)
- [Troubleshooting](TROUBLESHOOTING.md)
- [Turkish README](README_TR.md)

## 🏗️ Project Structure

```
vTarla/
├── pom.xml                 # Maven configuration
├── README.md               # This file
├── API.md                  # API documentation
├── CONFIGURATION.md        # Config guide
├── INSTALLATION.md         # Setup guide
├── TROUBLESHOOTING.md      # Common issues
└── src/main/java/com/vixians/tarla/
    ├── TarlaPlugin.java            # Main plugin class
    ├── api/                        # Public API
    ├── cache/                      # Caching system
    ├── coin/                       # Coin management
    ├── commands/                   # Command handlers
    ├── config/                     # Configuration
    ├── database/                   # Database & backup
    ├── discount/                   # Discount system
    ├── events/                     # Custom events
    ├── farm/                       # Farm system
    ├── listeners/                  # Event listeners
    ├── logging/                    # Log management
    ├── market/                     # Market & GUI
    ├── multiplier/                 # Multiplier system
    ├── stats/                      # Player statistics
    └── utils/                      # Utility classes
```

## ⚙️ System Requirements

- Java 17 or higher
- Paper 1.21+
- 100MB disk space (increases with database size)

## 📊 Performance

- **Async Operations**: All heavy tasks run asynchronously
- **TPS Impact**: Negligible (< 0.1 TPS)
- **Memory Usage**: ~50MB base + player data
- **Database**: SQLite with automatic optimization
- **Update Rate**: Configurable (default: 5-minute saves)

## 🔌 API Example

```java
import com.vixians.tarla.api.TarlaAPI;

// Get player coins
long coins = TarlaAPI.getPlayerCoins(player);

// Add coins with multiplier
TarlaAPI.addPlayerCoins(player, 100);

// Check multiplier
long multiplier = TarlaAPI.getPlayerMultiplier(player);

// Set discount
TarlaAPI.setDiscount(25, "EventAdmin");
```

## 🎨 Configuration Example

```yaml
farm:
  world: "farm_world"
  auto-save-interval: 300
  auto-regen-interval: 600

coin:
  coins-per-crop: 10
  display-name: "&6Tarla Coin&r"
  earn-message: "&a+{coins} {coin_name}"

multiplier:
  tiers:
    gold:
      multiplier: 3.0
      price: 35000
      permission: "tarla.multiplier.gold"
      display-name: "&6Gold Multiplier &7(3.0x)"
```

## 📝 Commands

### Player Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/tarla help` | Show help menu | tarla.command |
| `/tarla stats` | View your stats | tarla.command |
| `/tarla profile` | Show your profile | tarla.command |
| `/tarla market` | Open marketplace | tarla.market |
| `/tarla multiplier` | Open multiplier GUI | tarla.multiplier |

### Admin Commands
| Command | Description | Permission |
|---------|-------------|------------|
| `/tarla reload` | Reload plugin | tarla.admin.reload |
| `/taladiscount set <num>` | Set discount % | tarla.admin.discount |
| `/taladiscount remove` | Remove discount | tarla.admin.discount |
| `/taladmin backup` | Create backup | tarla.admin.backup |
| `/taladmin stats` | Global stats | tarla.admin.* |

## 🐛 Support

- 📋 [Issues](https://github.com/Vixians/vTarla/issues) - Report bugs
- 💬 [Discussions](https://github.com/Vixians/vTarla/discussions) - Ask questions
- 📖 [Wiki](https://github.com/Vixians/vTarla/wiki) - Documentation

## 📦 Building from Source

```bash
# Clone repository
git clone https://github.com/Vixians/vTarla.git
cd vTarla

# Build with Maven
mvn clean package

# Output: target/vTarla-1.0.0.jar
```

## 📄 License

MIT License - See [LICENSE](LICENSE) file for details

## 👨‍💻 Author

**Vixians** - Minecraft Plugin Developer
- GitHub: [@Vixians](https://github.com/Vixians)
- Website: [vixians.com](https://vixians.com)

## 🙏 Acknowledgments

- Paper Team for excellent Minecraft server software
- SQLite for reliable embedded database
- The Minecraft community for inspiration

## 📈 Roadmap

- [ ] Web dashboard for statistics
- [ ] Advanced analytics and reporting
- [ ] Mobile app integration
- [ ] Multi-world support
- [ ] Economy integration (Vault)
- [ ] Seasonal events system
- [ ] Leaderboards
- [ ] More multiplier customization

---

**Made with ❤️ for Minecraft Server Owners**

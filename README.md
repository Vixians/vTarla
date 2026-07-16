# vTarla - Advanced Farm Plugin

**vTarla** is a comprehensive, high-performance farming plugin for Paper Minecraft server (1.21+). Built with async operations to minimize TPS impact.

## Features

✨ **Core Features:**
- 🌾 Advanced farm system with automatic crop regeneration
- 💰 Custom coin system with async database storage
- 🔄 Multiplier system with permission-based tiers
- 🎁 Dynamic market with GUI
- 📊 Discount management system
- 📈 ActionBar & BossBar displays
- 💾 SQLite database with automatic backups
- ⚡ Fully async - Zero TPS impact
- 🔧 Fully customizable via config.yml
- 🛡️ Permission-based access control

## Installation

1. Download the latest JAR from releases
2. Place it in your `plugins` folder
3. Restart your server
4. Configure `plugins/vTarla/config.yml` as needed
5. Reload the plugin with `/tarla reload`

## Commands

### Player Commands
```
/tarla help              - Show help menu
/tarla stats             - Display your statistics
/tarla profile           - Show your profile
/tarla market            - Open the market
/tarla multiplier        - Open multiplier menu
```

### Admin Commands
```
/tarla reload            - Reload the plugin
/taladiscount set <num>  - Set global discount
/taladiscount remove     - Remove discount
```

## Permissions

```
tarla.*                  - Full access
tarla.command            - Use base command
tarla.market             - Access market
tarla.multiplier         - Access multiplier system
tarla.admin.*            - All admin permissions
tarla.admin.reload       - Reload plugin
tarla.admin.discount     - Manage discounts
tarla.admin.multiplier   - Manage multiplier tiers
```

## Configuration

### Farm Settings
```yaml
farm:
  world: "farm_world"               # Farm world name
  auto-save-interval: 300            # Save interval in seconds
  auto-regen-interval: 600           # Crop regen interval
```

### Coin System
```yaml
coin:
  coins-per-crop: 10                # Base coins earned
  display-name: "&6Tarla Coin&r"    # Display name
  earn-message: "&a+{coins} {coin_name}"
```

### Multiplier Tiers
```yaml
multiplier:
  tiers:
    bronze:
      multiplier: 1.5
      price: 5000
      permission: "tarla.multiplier.bronze"
```

## Architecture

- **Async Processing:** All heavy operations run asynchronously
- **SQLite Database:** Persistent data storage
- **Memory Cache:** In-memory caching for instant access
- **Event-Based:** Listens to crop breaks and updates coins
- **Configuration Driven:** Everything customizable

## Performance

- ✅ Zero TPS impact with async tasks
- ✅ Automatic database cleanup
- ✅ Efficient memory usage
- ✅ Optimized queries

## Support

For issues, questions, or suggestions, please open an issue on GitHub.

## License

MIT License - Feel free to use and modify!

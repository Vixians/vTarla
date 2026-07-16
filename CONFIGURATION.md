# Configuration Guide

## Farm World Settings

```yaml
farm:
  world: "farm_world"              # Name of the farm world
  auto-save-interval: 300          # Save data every 5 minutes
  auto-regen-interval: 600         # Regenerate crops every 10 minutes
```

**world**: The world where crops will not drop items normally but instead generate coins.

**auto-save-interval**: How often (in seconds) to save all player data to the database.

**auto-regen-interval**: How long (in seconds) before a broken crop regenerates.

## Coin System

```yaml
coin:
  coins-per-crop: 10                    # Base coins earned per crop break
  display-name: "&6Tarla Coin&r"       # Display name for coins
  earn-message: "&a+{coins} {coin_name}"
  database-save-interval: 300
```

## Multiplier Tiers

```yaml
multiplier:
  enabled: true
  default-multiplier: 1.0
  tiers:
    bronze:
      multiplier: 1.5
      price: 5000
      permission: "tarla.multiplier.bronze"
      display-name: "&8Bronze Multiplier &7(1.5x)"
```

### Adding New Multiplier Tiers

Add a new entry under `multiplier.tiers`:

```yaml
    custom:
      multiplier: 2.5
      price: 25000
      permission: "tarla.multiplier.custom"
      display-name: "&dCustom Multiplier &7(2.5x)"
```

Then add the permission to your permission plugin or `permissions.yml`.

## Market Items

```yaml
market:
  title: "&6Tarla Market"
  items:
    diamond:
      name: "&bDiamond Block"
      material: "DIAMOND_BLOCK"
      price: 1000
      amount: 1
      lore:
        - "&7Price: 1000 Tarla Coins"
      command: "none"  # Or use commands
```

### Adding Items with Commands

```yaml
    vip:
      name: "&5VIP 30 Days"
      material: "AMETHYST_CLUSTER"
      price: 5000
      amount: 1
      command: "lp user {player} permission set vip.access true"
```

## Discount Settings

```yaml
discount:
  enabled: true
  current-discount: 0              # Current global discount %
  max-discount: 50                 # Maximum discount allowed
```

Set discount with: `/taladiscount set 20`

## Display Settings

### ActionBar

```yaml
display:
  actionbar:
    enabled: true
    format: "&6Coins: {coins} &7| &aMultiplier: &6{multiplier}x &7| &6Discount: {discount}%"
    update-interval: 60
```

**Variables:**
- `{coins}` - Player's current coins
- `{multiplier}` - Player's multiplier value
- `{discount}` - Current global discount

### BossBar

```yaml
  bossbar:
    enabled: true
    title: "&6Coins: {coins} &7| &aMultiplier: &6{multiplier}x"
    color: "GOLD"                 # Colors: PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE
    style: "SEGMENTED_10"         # Styles: SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20
    update-interval: 60
```

## Messages

```yaml
messages:
  prefix: "&6[vTarla] &r"
  coin-earn: "{prefix}&a+{coins} Tarla Coin! Total: {total}"
  coin-insufficient: "{prefix}&cYou don't have enough coins!"
  coin-purchase-success: "{prefix}&aYou purchased {item}! &c-{price} coins"
  multiplier-purchased: "{prefix}&aMultiplier &6{tier} &apurchased! &c-{price} coins"
  discount-set: "{prefix}&aDiscount set to &6{discount}%"
```

## Crops

```yaml
crops:
  tracked-crops:
    - "WHEAT"
    - "CARROTS"
    - "POTATOES"
    - "BEETROOTS"
    - "NETHER_WART"
    - "COCOA_BEANS"
```

Add more crop types to track.

## Color Codes

Use `&` for color codes:
- `&0` - Black
- `&1` - Dark Blue
- `&2` - Dark Green
- `&3` - Dark Aqua
- `&4` - Dark Red
- `&5` - Dark Purple
- `&6` - Gold
- `&7` - Gray
- `&8` - Dark Gray
- `&9` - Blue
- `&a` - Green
- `&b` - Aqua
- `&c` - Red
- `&d` - Light Purple
- `&e` - Yellow
- `&f` - White

## Material Names

Valid Minecraft materials for market items:

- Blocks: `DIAMOND_BLOCK`, `GOLD_BLOCK`, `EMERALD_BLOCK`, `AMETHYST_CLUSTER`
- Items: `DIAMOND`, `GOLD_INGOT`, `EMERALD`, `APPLE`
- And any other Minecraft material

See full list: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html

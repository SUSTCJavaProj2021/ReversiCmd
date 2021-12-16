# ReversiCmd

## GameSystem

```java
public static void saveSettings(File file);
```

保存玩家数据和初始棋盘到 `file` 中。

（建议在应用关闭时调用）

```java
public static void loadSettings(File file);
```

从 `file` 中读取玩家数据和初始棋盘。

（建议在应用启动时调用）

```java
public static List<Player> getPlayerList();
```

获取玩家列表。

**返回：**玩家列表。

```java
public static void setInitialBoard(Board initialBoard);
```

将初始棋盘设置为 `initialBoard`。

```java
public static Game newGame(Player[] player, boolean[] cheatMode, String name);
```

以 `player[0]` 为黑方玩家，`player[1]` 为白方玩家，`cheatMode` 为对应玩家的作弊模式是否开启，`name` 为游戏名，默认初始棋盘为棋盘新建一局游戏。

若 `player` 中的人类玩家不在 `playerList` 中则加入。

**返回：**新建的游戏。

```java
public static Game newGame(Player[] player, boolean[] cheatMode, String name, Board board);
```

以 `player[0]` 为黑方玩家，`player[1]` 为白方玩家，`cheatMode` 为对应玩家的作弊模式是否开启，`name` 为游戏名，`board` 为棋盘新建一局游戏。

若 `player` 中的人类玩家不在 `playerList` 中则加入。

**返回：**新建的游戏。

```java
public static void saveGame(File file, Game game);
```

将 `game` 中的游戏存储到 `file` 中。

```java
public static Game loadGame(File file);
```

从 `file` 中读取一局游戏。

**读取的游戏的人类玩家必须在玩家列表中。**

**返回：**读取的游戏。

## Game

```java
public Board getBoard();
```

得到棋盘。

**返回：**游戏中的棋盘。

```java
public String getName();
```

得到游戏名。

**返回：**游戏名。

```java
public void setName(String name);
```

设置游戏名。

```java
public Player getBlackPlayer();
```

得到黑方玩家。

**返回：**黑方玩家。

```java
public void setBlackPlayer(Player player);
```

设置黑方玩家。

```java
public Player getWhitePlayer();
```

得到白方玩家。

**返回：**白方玩家。

```java
public void setWhitePlayer(Player player);
```

设置白方玩家。

```java
public Player getCurrentPlayer();
```

得到当前玩家。

**返回：**当前玩家。

```java
public void setCurrentPlayer(Player player);
```

设置当前玩家。

```java
public ChessColor getColor();
```

得到当前棋手的颜色。

**返回：**当前棋手颜色。

```java
public boolean isCheatMode();
```

判断当前玩家是否处于作弊模式。

**返回：**当前玩家是否处于作弊模式。

```java
public void setCheatMode(boolean cheatMode);
```

设置当前玩家是否进入作弊模式。

```java
public boolean isEnded();
```

判断游戏是否已结束。

**返回：**游戏是否已结束。

```java
public List<Step> getStepList();
```

得到游戏的步骤列表。

**返回：**游戏的步骤列表。

```java
public LocalDateTime getCreateTime();
```

得到游戏的创建时间。

**返回：**游戏的创建时间。

```java
public LocalDateTime getLastModifiedTime();
```

得到游戏的最后一次修改时间。

这里的修改指下一步棋。

**返回：**游戏的最后一次修改时间。

```java
public boolean pause();
```

停一手。

**只应当在当前棋手无法下棋的情况下自动调用。**

**返回：**是否应当结束游戏（连停两手）。

```java
public boolean move(int i, int j);
```

令当前玩家在 (i,j) 位置上下一手棋。

**该位置必须合法。**

**返回：**下一位玩家是否可以下棋。

```java
public void backToStart();
```

重置回游戏创建时的棋盘（已被修改的棋盘大小不会重置）。

在读档时重演示已下的手数时使用。

```java
public void performStep(int index);
```

下 `stepList[index]` 对应的棋。

在读档时重演示已下的手数时使用，从 `0` 到 `stepList.size() - 1` 依次调用。

```java
public boolean undo();
```

撤销最后下的一步棋。

**返回：**是否撤销成功（若 `stepList` 为空则返回 `false`）。

```java
public List<int[]> getPossibleMoves();
```

得到当前可下的所有位置。

**返回：**当前可下的所有位置列表。

```java
public boolean isMovable();
```

判断当前玩家是否可下棋。

**返回：**当前玩家是否可下棋。

```java
public Player endGame();
```

结束本局游戏。

根据棋盘上棋子个数的多少判断输赢或平局。

**返回：**胜利玩家（若平局则返回 `null`）。

## Board

```java
public static final int MAX_SIZE = 20;
```

棋盘最大大小。

```java
public Board(Board board);
```

使用另一个棋盘构造此棋盘。

```java
public Board(int rowSize, int columnSize, List<Chess> chessList, List<int[]> bannedPositions);
```

给定大小、初始棋子列表、禁用的位置列表，构造棋盘。

```java
public Chess[][] getChess();
```

返回棋盘上的棋子列表。

**返回：**棋盘上的棋子列表（`null` 代表该位置被禁用或在棋盘尺寸外而非不存在棋子）。

```java
public int getRowSize();
```

返回棋盘行数。

**返回：**棋盘行数；

```java
public int getColumnSize();
```

返回棋盘列数。

**返回：**棋盘列数；

```java
public boolean setSize(int rowSize, int columnSize);
```

重设定棋盘大小。

**不能缩小棋盘（新的行数和列数必须大于等于原先的）。**

**返回：**是否成功设定大小。

```java
public boolean isValid(int i, int j);
```

位置 (i,j) 是否合法（不合法：超出棋盘大小或被禁用）。

**返回：**位置 (i,j) 是否合法；

```java
public List<int[]> getBannedPositions();
```

得到被禁用的位置列表。

**返回：**被禁用的位置列表。

```java
public void printToCmd();
```

将棋盘打印到控制台中。

## Step

```java
public Player getPlayer();
```

得到下这步棋的玩家。

**返回：**下这步棋的玩家。

```java
public boolean isPause();
```

判断这步棋是否是停一手。

**返回：**这步棋是否是停一手。

```java
public Chess getChess();
```

得到这步棋下的棋子。

**返回：**这步棋下的棋子。

```java
public List<Chess[]> getModifiedChessList();
```

得到这步棋在棋盘上改变的棋子列表。

**返回：**这步棋在棋盘上改变的棋子列表（`list` 中的 `element[0]` 代表原棋子，`element[1]` 代表改变后的棋子）。

## ChessColor

```java
public enum ChessColor {
    BLACK, WHITE, NULL;
}
```

黑色、白色、不存在玩家下的棋子。

## Chess

```java
public Chess(Chess chess);
```

用另一个棋子构造这个棋子。

```java
public Chess(int rowIndex, int columnIndex);
```

以 `(rowIndex, columnIndex)` 为坐标构造一个颜色为 `NULL` 的棋子。

```java
public Chess(ChessColor color, int rowIndex, int columnIndex);
```

以 `(rowIndex, columnIndex)` 为坐标构造一个颜色为 `color` 的棋子。

```java
public ChessColor getColor();
```

得到棋子的颜色。

**返回：**棋子的颜色。

```java
public void setColor(ChessColor color);
```

设置棋子的颜色。

```java
public int[] getPosition();
```

得到棋子的位置。

**返回：**一个大小为 `2` 的 `int` 数组，分别代表该棋子的行坐标和列坐标。

```java
public int getRowIndex();
```

得到棋子的行坐标。

**返回：**棋子的行坐标。

```java
public int getColumnIndex();
```

得到棋子的列坐标。

**返回：**棋子的列坐标。

```java
public void setPosition(int rowIndex, int columnIndex);
```

设置棋子的位置。

## Player

### 继承结构

- `Player`
  - `HumanPlayer`
  - `AIPlayer`
    - `EasyAIPlayer`
    - ` NormalAIPlayer`
    - ` HardAIPlayer`
    - ` HellAIPlayer`

其中 `Player` 与 `AIPlayer` 都是抽象类。

### 使用方法

#### 人类玩家

直接新建 `HumanPlayer` 即可。

#### AI

使用 `Mode.EASY/NORMAL/HARD/HELL.getPlayer()` 调用。

### 内部类

#### GameCounter

用于存储玩家对局数据。

**只有已结束的对局才会被计算。**

```java
public int humanCount();
```

获得该类中玩家对人类的总对局数。

**返回：**该类中玩家对人类的总对局数。

```java
public int AICount();
```

获得该类中玩家对AI的总对局数。

**返回：**该类中玩家对AI的总对局数。

```java
public int playerCount(int pid);
```

获得该类中玩家对 `pid` 代表的玩家的对局数。

**返回：**该类中玩家对 `pid` 代表的玩家的对局数。

### 方法

```java
public Player(String name);
```

以 `name` 为名字构造一个 `Player`。

```java
public static int getPlayerCnt();
```

得到**人类**玩家数量。

**返回：人类**玩家数量。

```java
public boolean isHuman();
```

判断是否为人类玩家。

**返回：**是否为人类玩家。

```java
public int getPid();
```

获得该玩家的 `pid`。

**返回：**该玩家的 `pid`。

```java
public String getName();
```

获得该玩家的名字。

**返回：**该玩家的名字。

**以下方法仅对人类玩家实例可用，对AI玩家实例调用这些方法会返回 `null` 或 `-1.0`。**

```java
public abstract void setName(String name);
```

设置玩家的名字。

```java
public abstract GameCounter winCounter();
public abstract GameCounter loseCounter();
public abstract GameCounter totalCounter();
```

得到胜利/失败/所有对局的计数器。

**返回：**胜利/失败/所有对局的计数器。

```java
public abstract double calculateWinRateToHuman();
public abstract double calculateWinRateToAI();
public abstract double calculateWinRate(int pid);
```

计算对人类/AI/特定玩家的胜率。

**返回：**对人类/AI/特定玩家的胜率。

**以下方法仅对AI玩家实例可用，对人类玩家实例调用这些方法会直接报错。**

```java
public abstract int[] nextStep(Game game);
```

以 `game` 为参数，询问该AI玩家的下一步。

**返回：**该AI玩家的下一步。

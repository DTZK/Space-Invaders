# Space-Invaders
How to run code: grade run, no quirks


Remarks: Game lags sometimes


State Design Pattern:
ConcreteStates: ChangeGreen, ChangeYellow, ChangeRed
State: ColourChange
Context: Bunker
Client: GameEngine


Strategy Design Pattern:
Strategy: EnemyBullet
ConcreteStrategies: FastBullet, SlowBullet
Context:  AlienBullet
Client: Alien

Factory:
Creator: BulletCreator
Product: Bullet
ConcreteProduct: PlayerBullet, AlienBullet
ConcreteCreator: Player, Alien


Builder
Director: Director
Builder: Builder
ConcreteBuilders: AlienBuilder, BunkerBuilder
Product:Alien, Bunker
Client:GameEngine

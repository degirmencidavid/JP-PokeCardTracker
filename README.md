# JP-PokeCardTracker V0.1
A simple application to track prices of Pokemon cards on Japanese card shops.

Most functionality has ben completed for cardrush-pokemon.jp and needs to be implemented for other sites. The language drop-box is currently redundant, but will in future be used to switch between a set of English/Japanese storefronts.

Currently the **Poke Name**, **Set No**, **Set Limit**, **JPY Price**, **GBP price** fields have to be filled for a card to be generated. In future, JPY Price and GBP Price will be optional and will be used to compare your purchase price with the price on a certain storefront.

When a PokeCard is created, a small text field will also be filled in the bottom right of the application containing some data. This is for debugging.

# Functionality

When PokeTracker is launched, you will see the following:

![image](https://github.com/degirmencidavid/JP-PokeCardTracker/assets/101801691/fc72f078-17f2-4aba-9ccb-95c159795fdb)

The following fields must be filled:

![image](https://github.com/degirmencidavid/JP-PokeCardTracker/assets/101801691/6ab101e9-f5a1-4e6d-ae1c-260e65dc54f9)

The other fields can be filled to be more specific if needed. The data of a card can be found on the card itself (specific layout varies with each generation of cards, but the data itself is always similar).
NB - not all cards have a **Card Type**, **Rarity**, or even **Set Number/Limit** but most do.

## Annotated image of a the card being searched for in the above image

![charizardannotated](https://github.com/degirmencidavid/JP-PokeCardTracker/assets/101801691/004aa5f6-3600-4e55-be65-2fceff3baad3)

## Output

![image](https://github.com/degirmencidavid/JP-PokeCardTracker/assets/101801691/88b11274-79aa-4df5-a392-6830dda03b68)

Ambiguously, "Set Number" refers to the Set ID, in this card's case, the set S12a is the set "VSTAR Universe" - https://jp.pokellector.com/VSTAR-Universe-Expansion/

The price on the selected store (here it is cardrush-pokemon.jp) is obtained and converted to £GBP (with the intention to implement other curencies in the future), along with the card's name and stock. I have made sure to exclude any cards in damaged or lower than A (highest) condition.


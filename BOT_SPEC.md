

# Action block
Каждый блок действий имеет реакции на каждое возможное действие бота. 
Список возможных действий юзера детерминирован [TEXT, EMOJI, PHOTO, VIDEO, AUDIO, FILE, GIF, ... ]
Блок может иметь реакции по умолчанию на каждый UserInputType

## ImplNode
- onNext - если есть, то двигаемся дальше
- onError - если случился exception
- onText
- onImage
- onButton

??? Все реакции кроме [onNext, onError] завершают обработку current update и ждут следующего ??? 
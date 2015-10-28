package pnb.orp.cache;

import java.util.UUID;

import com.google.inject.Singleton;

import pnb.orp.characters.Character;
import pnb.orp.characters.Character.CharacterBuilder;

@Singleton
public interface ORPCache {
	
	//TODO Character creation methods
	
	//Database Cache Methods
	Character loadCharacter(UUID uuid, String cardName);
	Character loadCharacterAndMakeActive(UUID uuid, String cardName);
	CharacterBuilder loadCharacterBuilder(UUID uuid, String cardName);
	void shutdownCache();
}

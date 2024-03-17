This is the second implementation of a mod commission to make Minecraft axolotls tameable

Credits:</br>
The creation of this mod would not be possible without the help of the people on the Fabric MC discord server, including
- @longjawed, the original commissioner
- @ramixin, who wrote the first implementation which you can find [here](https://modrinth.com/mod/axoamigos)
- @llamalad7, who taught me the basics of mixins and helped me read and understand the [original mixin](https://github.com/RamGemes/tamableAxolotls/blob/master/src/main/java/net/ramgames/tamableaxolotls/mixins/AxolotlEntityMixin.java)
- @bawnorton, who wrote most (all) of the raw ASM and other things that I just don't understand
- @arkosammy12, who did an in-depth look at the original mixin along with @megal_, @dicedpixels, and @bawnorton
- @linguardium, who was patient enough with me when I didn't understand Java concepts

Features:
- Axolotls can be tamed and breeded with tropical fish and buckets of tropical fish
- Axolotls will follow and teleport with their owners understand
- Axolotls will attack their owner's target or their owner's attacker
- Axolotls will now remove all harmful status effects and gain strength, fire resistance, and absorption (also with the regeneration) upon playing dead

Q&A

Why does this exist if there's already another implementation?</br>
This implementation offers the same level of functionality while also being more easily compatible with other mods


Is this mod compatible with (insert another mod here)?</br>
As of now, this should be compatible with
- [Axolotl Bucket Fix](https://www.curseforge.com/minecraft/mc-mods/axolotl-bucket-fix)
- [Respawnable Pets](https://modrinth.com/mod/respawnable-pets)

It also should be compatible with other axolotl mods, but I haven't tested them</br>
If you find any issues, please report them [here](https://github.com/ekulxam/TameableAxolotls/issues)

Can I port this mod to (something that isn't Fabric)?</br>
Just please make sure to provide credit.
Yes, you can port to Forge / Neoforge.
Porting to Quilt might not be necessary (Fabric mods can run on Quilt) but you can do it anyways.
Yes, you can port to bedrock.

Can I use this mod in a modpack?</br>
Yes, just please make sure to provide credit.

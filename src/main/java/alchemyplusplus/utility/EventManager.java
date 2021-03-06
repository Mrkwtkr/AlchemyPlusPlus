package alchemyplusplus.utility;

import alchemyplusplus.ItemRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class EventManager
{

	public static void registerEventHooks()
	{
		MinecraftForge.EVENT_BUS.register(new EventManager());
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e)
	{
		if (e.entityLiving instanceof EntityPlayer && e.source == DamageSource.anvil)
		{
			ItemStack stack = new ItemStack(ItemRegistry.confusionPotion, 1, 0);
			Random random = new Random();

			stack.stackTagCompound = new NBTTagCompound();
			stack.getTagCompound().setString("owner", ((EntityPlayer) e.entityLiving).getDisplayName());

			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(e.entityLiving.worldObj, (double) ((float) e.entityLiving.posX + f), (double) ((float) e.entityLiving.posY + f1), (double) ((float) e.entityLiving.posZ + f2),
					stack);

			entityitem.motionX = (double) ((float) random.nextGaussian() * 0.05F);
			entityitem.motionY = (double) ((float) random.nextGaussian() * 0.05F + 0.2F);
			entityitem.motionZ = (double) ((float) random.nextGaussian() * 0.05F);

			e.entityLiving.worldObj.spawnEntityInWorld(entityitem);

		} else if (e.entityLiving instanceof EntitySquid)
		{
			if (e.source.getSourceOfDamage() != null && e.source.getSourceOfDamage() instanceof EntityPlayer)
			{
				ItemStack stack = (((EntityPlayer) e.source.getSourceOfDamage()).getHeldItem());
				if (stack.getItem() == Items.shears
						&& EnchantmentHelper.getSilkTouchModifier((EntityLivingBase) e.source.getSourceOfDamage())
						&& stack.hasDisplayName()
						&& stack.getDisplayName().toLowerCase().equals("eye choppa"))
				{

					ItemStack eyeStack = new ItemStack(ItemRegistry.squidEye, 1, 0);
					Random random = new Random();
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;

					EntityItem entityitem = new EntityItem(e.entityLiving.worldObj, (double) ((float) e.entityLiving.posX + f), (double) ((float) e.entityLiving.posY + f1), (double) ((float) e.entityLiving.posZ + f2),
							eyeStack);

					entityitem.motionX = (double) ((float) random.nextGaussian() * 0.05F);
					entityitem.motionY = (double) ((float) random.nextGaussian() * 0.05F + 0.2F);
					entityitem.motionZ = (double) ((float) random.nextGaussian() * 0.05F);

					e.entityLiving.worldObj.spawnEntityInWorld(entityitem);

					stack.damageItem(stack.getMaxDamage() + 1, e.entityLiving);
				}
			}
		} else if (e.entityLiving instanceof EntitySlime && e.source == DamageSource.fall)
		{

			ItemStack stack = new ItemStack(ItemRegistry.springyCord, 1, 0);
			Random random = new Random();
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(e.entityLiving.worldObj, (double) ((float) e.entityLiving.posX + f), (double) ((float) e.entityLiving.posY + f1), (double) ((float) e.entityLiving.posZ + f2),
					stack);

			entityitem.motionX = (double) ((float) random.nextGaussian() * 0.05F);
			entityitem.motionY = (double) ((float) random.nextGaussian() * 0.05F + 0.2F);
			entityitem.motionZ = (double) ((float) random.nextGaussian() * 0.05F);

			e.entityLiving.worldObj.spawnEntityInWorld(entityitem);

		} else if (e.entityLiving instanceof EntityIronGolem && e.source.getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) e.source.getSourceOfDamage();
			if (player.getHeldItem() == null)
			{
				Random random = new Random();
				if (random.nextFloat() >= 0.7f)
				{
					ItemStack stack = new ItemStack(ItemRegistry.ironPowder, 1, 0);

					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;

					EntityItem entityitem = new EntityItem(e.entityLiving.worldObj, (double) ((float) e.entityLiving.posX + f), (double) ((float) e.entityLiving.posY + f1), (double) ((float) e.entityLiving.posZ + f2),
							stack);

					entityitem.motionX = (double) ((float) random.nextGaussian() * 0.05F);
					entityitem.motionY = (double) ((float) random.nextGaussian() * 0.05F + 0.2F);
					entityitem.motionZ = (double) ((float) random.nextGaussian() * 0.05F);

					e.entityLiving.worldObj.spawnEntityInWorld(entityitem);
				}
			}
		}

	}

	@SubscribeEvent
	public void itemPickedUp(EntityItemPickupEvent e)
	{
		if (e.entityLiving instanceof EntityPlayer
				&& e.item.getEntityItem().getItem() == ItemRegistry.confusionPotion)
		{
			if (e.item.getEntityItem().hasTagCompound()
					&& e.item.getEntityItem().getTagCompound().getString("owner").equals(((EntityPlayer) e.entityLiving).getDisplayName()))
			{
				e.setCanceled(true);
			}
		}

	}

}

package jht3.colorchat;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClassTransformer implements IClassTransformer{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(transformedName.equals("net.minecraft.client.gui.GuiChat")){
			ClassNode classNode=new ClassNode();
			ClassReader classReader = new ClassReader(basicClass);
			classReader.accept(classNode, 0);
			Iterator<MethodNode> iterator=classNode.methods.iterator();
			FMLCommonHandler.instance().getFMLLogger().info("ColorChat ASMing.");
			while(iterator.hasNext()){
				MethodNode m=iterator.next();
				if(m.name.equals("a")&&m.desc.equals("(CI)V")){
					Iterator<AbstractInsnNode> i=m.instructions.iterator();
					while(i.hasNext()){
						AbstractInsnNode ai=i.next();
						if(ai.getOpcode()==Opcodes.IFNE){//&&(((JumpInsnNode)ai).label.getLabel()==8)){
							//System.out.println(((JumpInsnNode)ai).);
							m.instructions.insert(ai, new VarInsnNode(Opcodes.ASTORE,3));
							m.instructions.insert(ai, new MethodInsnNode(Opcodes.INVOKESTATIC, "jht3/colorchat/ColorChatCore","toColor","(Ljava/lang/String;)Ljava/lang/String;",false));
							m.instructions.insert(ai, new VarInsnNode(Opcodes.ALOAD,3));
						}
						//if(ai instanceof MethodInsnNode) System.out.println(((MethodInsnNode)ai).owner+" "+((MethodInsnNode)ai).name+" "+((MethodInsnNode)ai).desc);
						if(ai.getOpcode()==Opcodes.INVOKEVIRTUAL&&((MethodInsnNode)ai).owner.equals("bkn")&&((MethodInsnNode)ai).name.equals("f")){
							m.instructions.insertBefore(ai, new InsnNode(Opcodes.ICONST_0));
							m.instructions.set(ai, new MethodInsnNode(Opcodes.INVOKEVIRTUAL,"bkn","b","(Ljava/lang/String;Z)V",false));
						}
					}
				}
			}
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			return classWriter.toByteArray();
		}
		return basicClass;
	}
}

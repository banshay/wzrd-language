package com.banshay.language.nodes.toplevel;

import com.banshay.language.nodes.statements.WzrdBlockNode;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

// @ExportLibrary(NodeLibrary.class)
public abstract class WzrdNode extends Node {

  @CompilationFinal private volatile int visibleVarsIndexStart = -1;
  @CompilationFinal private volatile int visibleVarsIndexEnd = -1;

  //  public abstract Object execute(VirtualFrame frame);

  //  @ExportMessage
  //  public boolean hasScope(Frame frame) {
  //    return true;
  //  }

  //  @ExportMessage
  //  public Object getScope(
  //      Frame frame,
  //      boolean nodeEnter,
  //      @Cached(value = "this", adopt = false) WzrdNode cachedNode,
  //      @Cached(value = "this.findBlock()", adopt = false, allowUncached = true) Node blockNode) {
  //    if (blockNode instanceof WzrdBlockNode) {
  //      return new VariablesObject(frame, cachedNode, nodeEnter, (WzrdBlockNode) blockNode);
  //    }
  //    return new ArgumentsObject(frame, (WzrdRootNode) blockNode);
  //  }

  public final Node findBlock() {
    var parent = getParent();
    while (parent != null) {
      if (parent instanceof WzrdBlockNode) {
        break;
      }
      var parentParent = parent.getParent();
      if (parentParent == null) {
        assert parent instanceof RootNode : "Not adopted node under %s".formatted(parent);
        return parent;
      }
      parent = parentParent;
    }
    return parent;
  }

  //  @ExportLibrary(InteropLibrary.class)
  //  static final class ArgumentsObject implements TruffleObject {
  //
  //    static int LIMIT = 5;
  //
  //    private final Frame frame;
  //    protected final WzrdRootNode root;
  //
  //    ArgumentsObject(Frame frame, WzrdRootNode root) {
  //      this.frame = frame;
  //      this.root = root;
  //    }
  //
  //    //    @ExportMessage
  //    //    boolean isScope() {
  //    //      return true;
  //    //    }
  //    //
  //    //    @ExportMessage
  //    //    boolean hasLanguage() {
  //    //      return true;
  //    //    }
  //    //
  //    //        @ExportMessage
  //    //        boolean accepts(@Cached(value = "this.root", adopt = false) WzrdRootNode
  // cachedRoot) {
  //    //          return this.root == cachedRoot;
  //    //        }
  //    //
  //    //    @ExportMessage
  //    //    Class<? extends TruffleLanguage<?>> getLanguage() {
  //    //      return WzrdLanguage.class;
  //    //    }
  //    //
  //    //    @ExportMessage
  //    //    Object toDisplayString(boolean allowSideEffects) {
  //    //      return root.getName();
  //    //    }
  //    //
  //    //    @ExportMessage
  //    //    boolean hasMembers() {
  //    //      return true;
  //    //    }
  //    //
  //    //    @ExportMessage
  //    //    Object getMembers(boolean includeInternal) {
  //    //      return root.getDeclaredArguments();
  //    //    }
  //    //
  //    //    @ExportMessage
  //    //    boolean isMemberInsertable(String member) {
  //    //      return false;
  //    //    }
  //    //
  //    //    @ExportMessage(name = "isMemberReadable")
  //    static final class ExistsMember {
  //
  //      @Specialization(limit = "LIMIT", guards = "cachedMember.equals(member)")
  //      static boolean doCached(
  //          ArgumentsObject receiver,
  //          String member,
  //          @Cached("member") String cachedMember,
  //          @Cached("doGeneric(receiver, member)") boolean cachedResult) {
  //        assert cachedResult == doGeneric(receiver, member);
  //        return cachedResult;
  //      }
  //
  //      @Specialization(replaces = "doCached")
  //      @TruffleBoundary
  //      static boolean doGeneric(ArgumentsObject receiver, String member) {
  //        return receiver.hasArgumentIndex(member);
  //      }
  //    }
  //
  //    //    @ExportMessage(name = "isMemberModifiable")
  //    static final class ModifiableMember {
  //
  //      @Specialization(
  //          limit = "LIMIT",
  //          guards = {"cachedMember.equals(member)"})
  //      static boolean doCached(
  //          ArgumentsObject receiver,
  //          String member,
  //          @Cached("member") String cachedMember,
  //          @Cached("receiver.hasArgumentIndex(member)") boolean cachedResult) {
  //        return cachedResult && receiver.frame != null;
  //      }
  //
  //      @Specialization(replaces = "doCached")
  //      @TruffleBoundary
  //      static boolean doGeneric(ArgumentsObject receiver, String member) {
  //        return receiver.findArgumentIndex(member) >= 0 && receiver.frame != null;
  //      }
  //    }
  //
  //    //    @ExportMessage
  //    static final class ReadMember {
  //
  //      @Specialization(
  //          limit = "LIMIT",
  //          guards = {"cachedMember.equals(member)"})
  //      static Object doCached(
  //          ArgumentsObject receiver,
  //          String member,
  //          @Cached("member") String cachedMember,
  //          @Cached("receiver.findArgumentIndex(member)") int index)
  //          throws UnknownIdentifierException {
  //        return doRead(receiver, cachedMember, index);
  //      }
  //
  //      @Specialization(replaces = "doCached")
  //      @TruffleBoundary
  //      static Object doGeneric(ArgumentsObject receiver, String member)
  //          throws UnknownIdentifierException {
  //        int index = receiver.findArgumentIndex(member);
  //        return doRead(receiver, member, index);
  //      }
  //
  //      private static Object doRead(ArgumentsObject receiver, String member, int index)
  //          throws UnknownIdentifierException {
  //        if (index < 0) {
  //          throw UnknownIdentifierException.create(member);
  //        }
  //        if (receiver.frame != null) {
  //          return receiver.frame.getArguments()[index];
  //        } else {
  //          return null;
  //        }
  //      }
  //    }
  //
  //    //    @ExportMessage
  //    static final class WriteMember {
  //
  //      @Specialization(
  //          limit = "LIMIT",
  //          guards = {"cachedMember.equals(member)"})
  //      static void doCached(
  //          ArgumentsObject receiver,
  //          String member,
  //          Object value,
  //          @Cached("member") String cachedMember,
  //          @Cached("receiver.findArgumentIndex(member)") int index)
  //          throws UnknownIdentifierException, UnsupportedMessageException {
  //        doWrite(receiver, member, index, value);
  //      }
  //
  //      @Specialization(replaces = "doCached")
  //      @TruffleBoundary
  //      static void doGeneric(ArgumentsObject receiver, String member, Object value)
  //          throws UnknownIdentifierException, UnsupportedMessageException {
  //        int index = receiver.findArgumentIndex(member);
  //        doWrite(receiver, member, index, value);
  //      }
  //
  //      private static void doWrite(ArgumentsObject receiver, String member, int index, Object
  // value)
  //          throws UnknownIdentifierException, UnsupportedMessageException {
  //        if (index < 0) {
  //          throw UnknownIdentifierException.create(member);
  //        }
  //        if (receiver.frame != null) {
  //          receiver.frame.getArguments()[index] = value;
  //        } else {
  //          throw UnsupportedMessageException.create();
  //        }
  //      }
  //    }
  //
  //    boolean hasArgumentIndex(String member) {
  //      return findArgumentIndex(member) >= 0;
  //    }
  //
  //    int findArgumentIndex(String member) {
  //      var writeNodes = root.getDeclaredArguments();
  //      for (int i = 0; i < writeNodes.length; i++) {
  //        var writeNode = writeNodes[i];
  //        if (member.equals(writeNode.getName())) {
  //          return i;
  //        }
  //      }
  //      return -1;
  //    }
  //  }
  //
  //  //  @ExportLibrary(InteropLibrary.class)
  //  static final class VariablesObject implements TruffleObject {
  //
  //    static int LIMIT = 5;
  //    private final Frame frame;
  //    protected final WzrdNode node;
  //
  //    private final boolean nodeEnter;
  //    protected final WzrdBlockNode block;
  //
  //    public VariablesObject(Frame frame, WzrdNode node, boolean nodeEnter, WzrdBlockNode block) {
  //      this.frame = frame;
  //      this.node = node;
  //      this.nodeEnter = nodeEnter;
  //      this.block = block;
  //    }
  //
  //    int getVisibleVariablesIndex() {
  //      assert node.visibleVarsIndexStart >= 0;
  //      assert node.visibleVarsIndexEnd >= 0;
  //      return nodeEnter ? node.visibleVarsIndexStart : node.visibleVarsIndexEnd;
  //    }
  //
  //    boolean hasWriteNode(String member) {
  //      return findWriteNode(member) != null;
  //    }
  //
  //    int findSlot(String member) {
  //      var writeNode = findWriteNode(member);
  //      if (writeNode != null) {
  //        return writeNode.getSlot();
  //      }
  //      return -1;
  //    }
  //
  //    LocalVariableWriteNode findWriteNode(String member) {
  //      var writeNodes = block.getDeclaredLocalVariables();
  //      var parentBlockIndex = block.getParentBlockIndex();
  //      var visibleVariablesIndex = getVisibleVariablesIndex();
  //      for (int i = 0; i < visibleVariablesIndex; i++) {
  //        var writeNode = writeNodes[i];
  //        if (member.equals(writeNode.getName())) {
  //          return writeNode;
  //        }
  //      }
  //      for (int i = parentBlockIndex; i < writeNodes.length; i++) {
  //        var writeNode = writeNodes[i];
  //        if (member.equals(writeNode.getName())) {
  //          return writeNode;
  //        }
  //      }
  //      return null;
  //    }
  //
  //    //    @ExportMessage(name = "isMemberReadable")
  //    static final class ExistsMember {
  //      @Specialization(limit = "LIMIT", guards = "cachedMember.equals(member)")
  //      static boolean doCached(
  //          VariablesObject receiver,
  //          String member,
  //          String cachedMember,
  //          @Cached("doGeneric(receiver, member)") boolean cachedResult) {
  //        assert cachedResult == doGeneric(receiver, member);
  //        return cachedResult;
  //      }
  //
  //      @TruffleBoundary
  //      @Specialization(replaces = "doCached")
  //      static boolean doGeneric(VariablesObject receiver, String member) {
  //        return receiver.hasWriteNode(member);
  //      }
  //    }
  //
  //    //    @ExportMessage(name = "isMemberModifiable")
  //    static final class ModifiableMember {
  //
  //      @Specialization(limit = "LIMIT", guards = "cachedMember.equals(member)")
  //      static boolean doCached(
  //          VariablesObject receiver,
  //          String member,
  //          @Cached("member") String cachedMember,
  //          @Cached("receiver.hasWriteNode(member)") boolean cachedResult) {
  //        return cachedResult && receiver.frame != null;
  //      }
  //
  //      @TruffleBoundary
  //      @Specialization(replaces = "doCached")
  //      static boolean doGeneric(VariablesObject receiver, String member) {
  //        return receiver.hasWriteNode(member) && receiver.frame != null;
  //      }
  //    }
  //
  //    //    @ExportMessage
  //    static final class ReadMember {
  //
  //      /**
  //       * If the member is cached, use the cached frame slot and read the value from it. Call
  // {@link
  //       * #doGeneric(VariablesObject, String)} otherwise.
  //       */
  //      @Specialization(
  //          limit = "LIMIT",
  //          guards = {"cachedMember.equals(member)"})
  //      @SuppressWarnings("unused")
  //      static Object doCached(
  //          VariablesObject receiver,
  //          String member,
  //          @Cached("member") String cachedMember,
  //          // We cache the member's frame slot for fast-path access
  //          @Cached("receiver.findSlot(member)") int slot)
  //          throws UnknownIdentifierException {
  //        return doRead(receiver, cachedMember, slot);
  //      }
  //
  //      /** The uncached version finds the member's slot and read the value from it. */
  //      @Specialization(replaces = "doCached")
  //      @TruffleBoundary
  //      static Object doGeneric(VariablesObject receiver, String member)
  //          throws UnknownIdentifierException {
  //        int slot = receiver.findSlot(member);
  //        return doRead(receiver, member, slot);
  //      }
  //
  //      private static Object doRead(VariablesObject receiver, String member, int slot)
  //          throws UnknownIdentifierException {
  //        if (slot == -1) {
  //          throw UnknownIdentifierException.create(member);
  //        }
  //        if (receiver.frame != null) {
  //          return receiver.frame.getValue(slot);
  //        } else {
  //          return null;
  //        }
  //      }
  //    }
  //
  //    //    @ExportMessage
  //    static final class WriteMember {
  //
  //      /*
  //       * If the member is cached, use the cached write node and use it to write the value.
  //       * Call {@link #doGeneric(VariablesObject, String, Object)} otherwise.
  //       */
  //      @Specialization(
  //          limit = "LIMIT",
  //          guards = {"cachedMember.equals(member)"})
  //      @SuppressWarnings("unused")
  //      static void doCached(
  //          VariablesObject receiver,
  //          String member,
  //          Object value,
  //          @Cached("member") String cachedMember,
  //          // We cache the member's write node for fast-path access
  //          @Cached(value = "receiver.findWriteNode(member)", adopt = false)
  //              LocalVariableWriteNode writeNode)
  //          throws UnknownIdentifierException, UnsupportedMessageException {
  //        doWrite(receiver, cachedMember, writeNode, value);
  //      }
  //
  //      /** The uncached version finds the write node and use it to write the value. */
  //      @Specialization(replaces = "doCached")
  //      @TruffleBoundary
  //      static void doGeneric(VariablesObject receiver, String member, Object value)
  //          throws UnknownIdentifierException, UnsupportedMessageException {
  //        LocalVariableWriteNode writeNode = receiver.findWriteNode(member);
  //        doWrite(receiver, member, writeNode, value);
  //      }
  //
  //      private static void doWrite(
  //          VariablesObject receiver, String member, LocalVariableWriteNode writeNode, Object
  // value)
  //          throws UnknownIdentifierException, UnsupportedMessageException {
  //        if (writeNode == null) {
  //          throw UnknownIdentifierException.create(member);
  //        }
  //        if (receiver.frame == null) {
  //          throw UnsupportedMessageException.create();
  //        }
  //        writeNode.executeWrite((VirtualFrame) receiver.frame, value);
  //      }
  //    }
  //  }

  public int getVisibleVarsIndexStart() {
    return visibleVarsIndexStart;
  }

  public void setVisibleVarsIndexStart(int index) {
    assert visibleVarsIndexStart == -1 : "The index is set just once";
    assert 0 <= index;
    this.visibleVarsIndexStart = index;
  }

  public int getVisibleVarsIndexEnd() {
    return visibleVarsIndexEnd;
  }

  public void setVisibleVarsIndexEnd(int index) {
    assert visibleVarsIndexEnd == -1 : "The index is set just once";
    assert 0 <= index;
    this.visibleVarsIndexEnd = index;
  }
}

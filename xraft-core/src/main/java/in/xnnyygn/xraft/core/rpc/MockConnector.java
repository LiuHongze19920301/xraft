package in.xnnyygn.xraft.core.rpc;

import in.xnnyygn.xraft.core.node.NodeEndpoint;
import in.xnnyygn.xraft.core.node.NodeId;
import in.xnnyygn.xraft.core.rpc.message.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MockConnector extends ConnectorAdapter {

    private LinkedList<Message> messages = new LinkedList<>();

    @Override
    public void sendRequestVote(RequestVoteRpc rpc, Collection<NodeEndpoint> destinationEndpoints) {
        Message m = new Message();
        m.rpc = rpc;
        messages.add(m);
    }

    @Override
    public void replyRequestVote(RequestVoteResult result, RequestVoteRpcMessage rpcMessage) {
        Message m = new Message();
        m.result = result;
        m.destinationNodeId = rpcMessage.getSourceNodeId();
        messages.add(m);
    }

    @Override
    public void sendAppendEntries(AppendEntriesRpc rpc, NodeEndpoint destinationEndpoint) {
        Message m = new Message();
        m.rpc = rpc;
        m.destinationNodeId = destinationEndpoint.getId();
        messages.add(m);
    }

    @Override
    public void replyAppendEntries(AppendEntriesResult result, AppendEntriesRpcMessage rpcMessage) {
        Message m = new Message();
        m.result = result;
        m.destinationNodeId = rpcMessage.getSourceNodeId();
        messages.add(m);
    }

    @Override
    public void sendInstallSnapshot(InstallSnapshotRpc rpc, NodeEndpoint destinationEndpoint) {
        Message m = new Message();
        m.rpc = rpc;
        m.destinationNodeId = destinationEndpoint.getId();
        messages.add(m);
    }

    @Override
    public void replyInstallSnapshot(InstallSnapshotResult result, InstallSnapshotRpcMessage rpcMessage) {
        Message m = new Message();
        m.result = result;
        m.destinationNodeId = rpcMessage.getSourceNodeId();
        messages.add(m);
    }

    public Message getLastMessage() {
        return messages.isEmpty() ? null : messages.getLast();
    }

    private Message getLastMessageOrDefault() {
        return messages.isEmpty() ? new Message() : messages.getLast();
    }

    public Object getRpc() {
        return getLastMessageOrDefault().rpc;
    }

    public Object getResult() {
        return getLastMessageOrDefault().result;
    }

    public NodeId getDestinationNodeId() {
        return getLastMessageOrDefault().destinationNodeId;
    }

    public int getMessageCount() {
        return messages.size();
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void clearMessage() {
        messages.clear();
    }

    public static class Message {

        private Object rpc;
        private NodeId destinationNodeId;
        private Object result;

        public Object getRpc() {
            return rpc;
        }

        public NodeId getDestinationNodeId() {
            return destinationNodeId;
        }

        public Object getResult() {
            return result;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "destinationNodeId=" + destinationNodeId +
                    ", rpc=" + rpc +
                    ", result=" + result +
                    '}';
        }

    }

}

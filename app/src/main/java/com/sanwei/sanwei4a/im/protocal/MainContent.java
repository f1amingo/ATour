// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MainContent.proto

package com.sanwei.sanwei4a.im.protocal;

public final class MainContent {
    private MainContent() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface MessageOrBuilder extends
            // @@protoc_insertion_point(interface_extends:com.sanwei.sanwei4a.im.protocal.Message)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <pre>
         * 主体消息类型
         * </pre>
         * <p>
         * <code>int32 type = 1;</code>
         */
        int getType();

        /**
         * <pre>
         * 时间戳
         * </pre>
         * <p>
         * <code>string timestamp = 2;</code>
         */
        String getTimestamp();

        /**
         * <pre>
         * 时间戳
         * </pre>
         * <p>
         * <code>string timestamp = 2;</code>
         */
        com.google.protobuf.ByteString
        getTimestampBytes();

        /**
         * <pre>
         * 主体消息
         * </pre>
         * <p>
         * <code>bytes content = 3;</code>
         */
        com.google.protobuf.ByteString getContent();
    }

    /**
     * Protobuf type {@code com.sanwei.sanwei4a.im.protocal.Message}
     */
    public static final class Message extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:com.sanwei.sanwei4a.im.protocal.Message)
            MessageOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use Message.newBuilder() to construct.
        private Message(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Message() {
            type_ = 0;
            timestamp_ = "";
            content_ = com.google.protobuf.ByteString.EMPTY;
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private Message(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownFieldProto3(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 8: {

                            type_ = input.readInt32();
                            break;
                        }
                        case 18: {
                            String s = input.readStringRequireUtf8();

                            timestamp_ = s;
                            break;
                        }
                        case 26: {

                            content_ = input.readBytes();
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return MainContent.internal_static_com_sanwei_sanwei4a_im_protocal_Message_descriptor;
        }

        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return MainContent.internal_static_com_sanwei_sanwei4a_im_protocal_Message_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Message.class, Builder.class);
        }

        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;

        /**
         * <pre>
         * 主体消息类型
         * </pre>
         * <p>
         * <code>int32 type = 1;</code>
         */
        public int getType() {
            return type_;
        }

        public static final int TIMESTAMP_FIELD_NUMBER = 2;
        private volatile Object timestamp_;

        /**
         * <pre>
         * 时间戳
         * </pre>
         * <p>
         * <code>string timestamp = 2;</code>
         */
        public String getTimestamp() {
            Object ref = timestamp_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                timestamp_ = s;
                return s;
            }
        }

        /**
         * <pre>
         * 时间戳
         * </pre>
         * <p>
         * <code>string timestamp = 2;</code>
         */
        public com.google.protobuf.ByteString
        getTimestampBytes() {
            Object ref = timestamp_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                timestamp_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int CONTENT_FIELD_NUMBER = 3;
        private com.google.protobuf.ByteString content_;

        /**
         * <pre>
         * 主体消息
         * </pre>
         * <p>
         * <code>bytes content = 3;</code>
         */
        public com.google.protobuf.ByteString getContent() {
            return content_;
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (type_ != 0) {
                output.writeInt32(1, type_);
            }
            if (!getTimestampBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 2, timestamp_);
            }
            if (!content_.isEmpty()) {
                output.writeBytes(3, content_);
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (type_ != 0) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(1, type_);
            }
            if (!getTimestampBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, timestamp_);
            }
            if (!content_.isEmpty()) {
                size += com.google.protobuf.CodedOutputStream
                        .computeBytesSize(3, content_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Message)) {
                return super.equals(obj);
            }
            Message other = (Message) obj;

            boolean result = true;
            result = result && (getType()
                    == other.getType());
            result = result && getTimestamp()
                    .equals(other.getTimestamp());
            result = result && getContent()
                    .equals(other.getContent());
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            hash = (37 * hash) + TYPE_FIELD_NUMBER;
            hash = (53 * hash) + getType();
            hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
            hash = (53 * hash) + getTimestamp().hashCode();
            hash = (37 * hash) + CONTENT_FIELD_NUMBER;
            hash = (53 * hash) + getContent().hashCode();
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static Message parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Message parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Message parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Message parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Message parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Message parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Message parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static Message parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Message parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static Message parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Message parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static Message parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Message prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code com.sanwei.sanwei4a.im.protocal.Message}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:com.sanwei.sanwei4a.im.protocal.Message)
                MessageOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return MainContent.internal_static_com_sanwei_sanwei4a_im_protocal_Message_descriptor;
            }

            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return MainContent.internal_static_com_sanwei_sanwei4a_im_protocal_Message_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                Message.class, Builder.class);
            }

            // Construct using com.sanwei.sanwei4a.im.protocal.MainContent.Message.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                type_ = 0;

                timestamp_ = "";

                content_ = com.google.protobuf.ByteString.EMPTY;

                return this;
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return MainContent.internal_static_com_sanwei_sanwei4a_im_protocal_Message_descriptor;
            }

            public Message getDefaultInstanceForType() {
                return Message.getDefaultInstance();
            }

            public Message build() {
                Message result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public Message buildPartial() {
                Message result = new Message(this);
                result.type_ = type_;
                result.timestamp_ = timestamp_;
                result.content_ = content_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof Message) {
                    return mergeFrom((Message) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(Message other) {
                if (other == Message.getDefaultInstance()) return this;
                if (other.getType() != 0) {
                    setType(other.getType());
                }
                if (!other.getTimestamp().isEmpty()) {
                    timestamp_ = other.timestamp_;
                    onChanged();
                }
                if (other.getContent() != com.google.protobuf.ByteString.EMPTY) {
                    setContent(other.getContent());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                Message parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (Message) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int type_;

            /**
             * <pre>
             * 主体消息类型
             * </pre>
             * <p>
             * <code>int32 type = 1;</code>
             */
            public int getType() {
                return type_;
            }

            /**
             * <pre>
             * 主体消息类型
             * </pre>
             * <p>
             * <code>int32 type = 1;</code>
             */
            public Builder setType(int value) {

                type_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 主体消息类型
             * </pre>
             * <p>
             * <code>int32 type = 1;</code>
             */
            public Builder clearType() {

                type_ = 0;
                onChanged();
                return this;
            }

            private Object timestamp_ = "";

            /**
             * <pre>
             * 时间戳
             * </pre>
             * <p>
             * <code>string timestamp = 2;</code>
             */
            public String getTimestamp() {
                Object ref = timestamp_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    timestamp_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <pre>
             * 时间戳
             * </pre>
             * <p>
             * <code>string timestamp = 2;</code>
             */
            public com.google.protobuf.ByteString
            getTimestampBytes() {
                Object ref = timestamp_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    timestamp_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <pre>
             * 时间戳
             * </pre>
             * <p>
             * <code>string timestamp = 2;</code>
             */
            public Builder setTimestamp(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                timestamp_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 时间戳
             * </pre>
             * <p>
             * <code>string timestamp = 2;</code>
             */
            public Builder clearTimestamp() {

                timestamp_ = getDefaultInstance().getTimestamp();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 时间戳
             * </pre>
             * <p>
             * <code>string timestamp = 2;</code>
             */
            public Builder setTimestampBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                timestamp_ = value;
                onChanged();
                return this;
            }

            private com.google.protobuf.ByteString content_ = com.google.protobuf.ByteString.EMPTY;

            /**
             * <pre>
             * 主体消息
             * </pre>
             * <p>
             * <code>bytes content = 3;</code>
             */
            public com.google.protobuf.ByteString getContent() {
                return content_;
            }

            /**
             * <pre>
             * 主体消息
             * </pre>
             * <p>
             * <code>bytes content = 3;</code>
             */
            public Builder setContent(com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                content_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * 主体消息
             * </pre>
             * <p>
             * <code>bytes content = 3;</code>
             */
            public Builder clearContent() {

                content_ = getDefaultInstance().getContent();
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFieldsProto3(unknownFields);
            }

            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:com.sanwei.sanwei4a.im.protocal.Message)
        }

        // @@protoc_insertion_point(class_scope:com.sanwei.sanwei4a.im.protocal.Message)
        private static final Message DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new Message();
        }

        public static Message getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<Message>
                PARSER = new com.google.protobuf.AbstractParser<Message>() {
            public Message parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new Message(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<Message> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<Message> getParserForType() {
            return PARSER;
        }

        public Message getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_com_sanwei_sanwei4a_im_protocal_Message_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_com_sanwei_sanwei4a_im_protocal_Message_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\021MainContent.proto\022\037com.sanwei.sanwei4a" +
                        ".im.protocal\";\n\007Message\022\014\n\004type\030\001 \001(\005\022\021\n" +
                        "\ttimestamp\030\002 \001(\t\022\017\n\007content\030\003 \001(\014b\006proto" +
                        "3"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        }, assigner);
        internal_static_com_sanwei_sanwei4a_im_protocal_Message_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_com_sanwei_sanwei4a_im_protocal_Message_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_com_sanwei_sanwei4a_im_protocal_Message_descriptor,
                new String[]{"Type", "Timestamp", "Content",});
    }

    // @@protoc_insertion_point(outer_class_scope)
}

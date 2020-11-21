package it.unibo.webrtc.signalling

import it.unibo.webrtc.common.Disposable
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

/**
 * Signalling client interface.
 *
 * Manages the connection with the signalling server.
 */
interface SignallingClient : Disposable {

    /**
     * Connects to the signalling server.
     * @param peerId The peer id; if not provided a unique id will be generated by the server).
     * @return The peer id.
     */
    suspend fun connect(peerId: String? = null): String

    /**
     * Disconnects from the signalling server.
     */
    fun disconnect()

    /**
     * Returns whether the client is connected to a server or not.
     * @return True, if the client is connected; otherwise false.
     */
    fun connected(): Boolean

    /**
     * Retrieves the list of active peers from the signalling server.
     * @return The list of all peer ids.
     */
    fun getActivePeers(): List<String>

    /**
     * Creates and sends a call offer to a remote peer.
     * @param peerId The remote peer id.
     * @param description The session description that represents the offer.
     * @param consumer A function that consumes the uniquely generated connection id.
     * @return The session description that represents the answer of the remote peer.
     */
    suspend fun call(peerId: String, description: SessionDescription, consumer: (String) -> Unit): SessionDescription

    /**
     * Creates and sends a data exchange offer to a remote peer.
     * @param peerId The remote peer id.
     * @param description The session description that represents the offer.
     * @param consumer A function that consumes the uniquely generated connection id.
     * @return The session description that represents the answer of the remote peer.
     */
    suspend fun exchangeData(peerId: String, description: SessionDescription, consumer: (String) -> Unit): SessionDescription

    /**
     * Answers to an offer.
     * @param connectionId The connection identifier.
     * @param description The description
     */
    fun answer(connectionId: String, description: SessionDescription)

    /**
     *Sends an ICE candidate to the remote peer.
     * @param connectionId The connection identifier.
     * @param candidate The ICE candidate.
     */
    fun sendCandidate(connectionId: String, candidate: IceCandidate)
}
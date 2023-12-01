"use client";

import Image from 'next/image'
import styles from './page.module.css'
import { useState } from 'react';

export default function Home() {
  const [prompt, setPrompt] = useState("");
  const [system, setSystem] = useState("");
  const [msg, setMsg] = useState("");

  function createPostRequest() {
    setMsg("...");
    fetch("/api/prompt", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        "prompt": prompt,
        "system_prompt": system
      })
    }).then((res) => res.json()).then((data) => {
      setMsg(data["msg"]);
    })
  }

  return (
    <main>
      <input value={prompt} onChange={(e) => setPrompt(e.target.value)} />
      <input value={system} onChange={(e) => setSystem(e.target.value)} />
      <button onClick={createPostRequest}>Submit</button>
      <div>
        <p>{ msg }</p>
      </div>
    </main>
  )
}

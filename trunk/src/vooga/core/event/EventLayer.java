package vooga.core.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import vooga.core.event.filters.IEventFilter;


/**
 * @author Michael Ansel
 * @author Ethan Goh
 */
public class EventLayer
{
    private LinkedList<IFiredEvent> myCurrentEventQueue;
    private List<IEventFilter> myEventFilters;
    private Map<String, IEventHandler> myEventHandlers;
    private LinkedList<IFiredEvent> myNextEventQueue;
    private EventLayer myParentLayer;

    private LinkedList<ITimer> myTimers;


    protected EventLayer ()
    {
        myEventHandlers = new HashMap<String, IEventHandler>();
        myCurrentEventQueue = new LinkedList<IFiredEvent>();
        myNextEventQueue = new LinkedList<IFiredEvent>();
        myTimers = new LinkedList<ITimer>();
        myEventFilters = new ArrayList<IEventFilter>();
    }


    /**
     * Create a new EventLayer with the given parent
     * 
     * @param parentLayer
     */
    public EventLayer (EventLayer parentLayer)
    {
        this(parentLayer, Arrays.asList(new IEventFilter[] {}));
    }


    public EventLayer (EventLayer parentLayer, List<IEventFilter> filters)
    {
        this();
        myParentLayer = parentLayer;
        myEventFilters = filters;
        resetToParentState();
    }


    public void addEvent (IFiredEvent event)
    {
        myNextEventQueue.add(event);
    }


    public PeriodicTimer addPeriodicTimer (EventManager eventManager,
                                           String eventName,
                                           long interval,
                                           Object arg)
    {
        return (PeriodicTimer) addTimer(new PeriodicTimer(eventManager,
                                                          interval,
                                                          eventName,
                                                          arg));
    }


    public Timer addTimer (EventManager eventManager,
                           String eventName,
                           long delay,
                           Object arg)
    {
        return (Timer) addTimer(new Timer(eventManager, delay, eventName, arg));
    }


    protected ITimer addTimer (ITimer timer)
    {
        myTimers.add(timer);
        return timer;
    }


    private String convertGlobToRegex (String glob)
    {
        StringBuilder out = new StringBuilder("^");
        for (int i = 0; i < glob.length(); ++i)
        {
            final char c = glob.charAt(i);
            switch (c)
            {
                case '*':
                    out.append(".*");
                    break;
                case '?':
                    out.append('.');
                    break;
                case '.':
                    out.append("\\.");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                default:
                    out.append(c);
            }
        }
        out.append('$');
        return out.toString();
    }


    public IEventHandler getEventHandler (String eventName)
    {
        return myEventHandlers.get(eventName);
    }


    public List<Map.Entry<String, IEventHandler>> getEventHandlerEntries (String glob)
    {
        Pattern pattern = Pattern.compile(convertGlobToRegex(glob));

        List<Map.Entry<String, IEventHandler>> result =
            new ArrayList<Map.Entry<String, IEventHandler>>();

        for (Map.Entry<String, IEventHandler> entry : myEventHandlers.entrySet())
            if (pattern.matcher(entry.getKey()).matches()) result.add(entry);

        return result;
    }


    public List<ITimer> getExpiredTimers ()
    {
        List<ITimer> expiredTimers = new LinkedList<ITimer>();
        for (ITimer timer : myTimers)
            if (timer.isReadyToFire()) expiredTimers.add(timer);
        return expiredTimers;
    }


    public EventLayer getParentFilter ()
    {
        return myParentLayer;
    }


    public boolean hasEvents ()
    {
        return !myCurrentEventQueue.isEmpty();
    }


    public void registerEventHandler (String eventName,
                                      IEventHandler eventHandler)
    {
        // Make sure the event name isn't a glob
        if (eventName.contains("*")) throw new IllegalArgumentException("Invalid character in event name: "
                                                                        + "'*' not allowed.");
        if (eventName.contains("?")) throw new IllegalArgumentException("Invalid character in event name: "
                                                                        + "'?' not allowed.");

        myEventHandlers.put(eventName, eventHandler);
    }


    public IFiredEvent removeEvent ()
    {
        return myCurrentEventQueue.remove();
    }


    public IEventHandler removeEventHandler (String eventName)
    {
        return myEventHandlers.remove(eventName);
    }


    public void removeTimer (ITimer timer)
    {
        myTimers.remove(timer);
    }


    public void resetToParentState ()
    {
        myEventHandlers.clear();

        for (IEventFilter filter : myEventFilters)
        {
            for (String s : this.getParentFilter().myEventHandlers.keySet())
            {
                if (!filter.isFiltered(s))
                {
                    myEventHandlers.put(s,
                                        this.getParentFilter().myEventHandlers.get(s));
                }
            }
        }

        myEventHandlers.putAll(this.getParentFilter().myEventHandlers);

        // Note: Don't clone event queues
        myCurrentEventQueue.clear();
        myNextEventQueue.clear();

        myTimers.clear();
        myTimers.addAll(this.getParentFilter().myTimers);
    }


    public void setParentFilter (EventLayer newParentFilter)
    {
        myParentLayer = newParentFilter;
    }


    public void swapEventQueues ()
    {
        LinkedList<IFiredEvent> temp = myCurrentEventQueue;
        myCurrentEventQueue = myNextEventQueue;
        // for memory efficiency, recycle the old queue
        myNextEventQueue = temp;
        myNextEventQueue.clear();
    }
}